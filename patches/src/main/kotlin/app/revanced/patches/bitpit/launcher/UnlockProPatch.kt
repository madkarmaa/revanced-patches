package app.revanced.patches.bitpit.launcher

import app.revanced.patcher.extensions.addInstruction
import app.revanced.patcher.patch.bytecodePatch

@Suppress("unused")
val unlockProPatch = bytecodePatch(
    name = "Unlock Pro",
    description = "Unlocks Pro features.",
) {
    compatibleWith("bitpit.launcher")

    dependsOn(signatureKillerPatch)

    apply {
        proCheckerConstructor.addInstruction(1, "const/4 p1, 0x1")
    }
}