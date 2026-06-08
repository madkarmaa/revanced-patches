rootProject.name = "madkarma-patches"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/revanced/registry")
            credentials {
                username = providers.gradleProperty("githubPackagesUsername").orNull ?: System.getenv("GITHUB_ACTOR")
                password = providers.gradleProperty("githubPackagesPassword").orNull ?: System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

plugins {
    id("app.revanced.patches") version "1.0.0-dev.11"
    id("com.android.library") version "7.3.0" apply false
}

include(":killer")