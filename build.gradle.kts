plugins {
    id("org.jetbrains.intellij.platform") version "2.11.0"
    kotlin("jvm") version "2.4.21"
}

version = "1.0.1"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    intellijPlatform {
        local("/Applications/Android Studio.app/Contents")
        bundledPlugin("org.jetbrains.android")
    }
}

intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "233"
            // No upper bound: the plugin only touches AndroidRunConfiguration.CLEAR_APP_STORAGE,
            // so a hard until-build just makes AS silently drop it after every major update.
            untilBuild = provider { null }
        }
    }

    buildSearchableOptions = false
}

kotlin {
    jvmToolchain(17)
}
