group = "app.madkarmaa"

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

dependencies {
    implementation(project(path = ":killer", configuration = "releaseRuntimeElements")) {
        isTransitive = false
    }
}