package top.madkarma.patches.bitpit.launcher

import app.revanced.patcher.extensions.addInstructions
import app.revanced.patcher.patch.bytecodePatch
import app.revanced.patcher.patch.resourcePatch
import com.android.apksig.ApkVerifier
import java.io.File
import java.security.cert.X509Certificate
import java.util.Base64

private val classLoader = object {}.javaClass.classLoader
private var extractedSignature = ""

private fun extractSignature(apkFile: File): String {
    val verifier: ApkVerifier = ApkVerifier.Builder(apkFile).build()
    val result = verifier.verify()

    var cert: X509Certificate? = null

    if (!result.v3SchemeSigners.isEmpty()) cert = result.v3SchemeSigners[0].certificate
    else if (!result.v2SchemeSigners.isEmpty()) cert = result.v2SchemeSigners[0].certificate
    else if (!result.v1SchemeSigners.isEmpty()) cert = result.v1SchemeSigners[0].certificate

    return Base64.getEncoder().encodeToString(
        checkNotNull(cert) { "No signature found in APK" }.encoded
    )
}

@Suppress("unused")
val signatureKillerResourcePatch = resourcePatch(
    name = "SignatureKiller resources",
    description = "Bundles the original APK and native libraries required for SignatureKiller.",
) {
    apply {
        // bundle original APK
        val originApk = get("assets/SignatureKiller").also { it.mkdirs() }.resolve("origin.apk")
        inputApkFileInputStream.use { it.copyTo(originApk.outputStream()) }

        extractedSignature = extractSignature(originApk)

        // bundle .so files built from :killer
        listOf("armeabi-v7a", "x86", "arm64-v8a", "x86_64").forEach { abi ->
            val libDir = get("lib/$abi").also { it.mkdirs() }
            val soStream = classLoader.getResourceAsStream("lib/$abi/libSignatureKiller.so")
            soStream.use { it!!.copyTo(libDir.resolve("libSignatureKiller.so").outputStream()) }
        }
    }
}

@Suppress("unused")
val signatureKillerPatch = bytecodePatch(
    name = "Bypass signature checks",
    description = "Bypasses signature verification using SignatureKiller.",
) {
    compatibleWith("bitpit.launcher")

    extendWith("extensions/signature-killer.rve")
    dependsOn(signatureKillerResourcePatch)

    apply {
        launcherAppOnCreate.addInstructions(
            0, """
                const-string v0, "bitpit.launcher"
                const-string v1, "$extractedSignature"
                invoke-static {v0, v1}, Lbin/mt/signature/extension/SignatureKiller;->kill(Ljava/lang/String;Ljava/lang/String;)V
            """
        )
    }
}