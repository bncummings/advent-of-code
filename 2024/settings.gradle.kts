pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "AdventOfCode"
include("src:main:lib")
findProject(":src:main:lib")?.name = "lib"
