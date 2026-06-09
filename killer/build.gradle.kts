plugins {
    id("com.android.library")
}

android {
    namespace = "bin.mt.signature"
    compileSdk = 37
    ndkVersion = "24.0.8215888"

    defaultConfig {
        minSdk = 21

        externalNativeBuild {
            cmake {
                cppFlags("")
                abiFilters += listOf("armeabi-v7a", "x86", "arm64-v8a", "x86_64")
            }
        }
        ndk {
            abiFilters += listOf("armeabi-v7a", "x86", "arm64-v8a", "x86_64")
        }
    }

    externalNativeBuild {
        cmake {
            path = file("CMakeLists.txt")
        }
    }
}