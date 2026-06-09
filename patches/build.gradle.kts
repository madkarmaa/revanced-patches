group = "top.madkarmaa"

patches {
    about {
        name = "MadKarma Patches"
        description = "My custom set of ReVanced patches"
        source = "git@github.com:madkarmaa/revanced-patches.git"
        author = "madkarmaa"
        contact = "me@madkarma.top"
        website = "https://madkarma.top"
        license = "GNU General Public License v3.0"
    }
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll(
            "-Xexplicit-backing-fields",
            "-Xcontext-parameters"
        )
    }
}

val extractNativeLibs by tasks.registering(Copy::class) {
    description = "Extracts native libraries from the killer module and places them in the resources directory."
    dependsOn(":killer:mergeReleaseNativeLibs")
    from(
        project(":killer").layout.buildDirectory.dir(
            "intermediates/merged_native_libs/release/mergeReleaseNativeLibs/out/lib"
        )
    )
    into("src/main/resources/lib")
}

tasks.named("processResources") {
    dependsOn(extractNativeLibs)
}

dependencies {
    implementation("com.android.tools.build:apksig:9.2.1")
}