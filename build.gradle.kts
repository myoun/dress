plugins {
    kotlin("jvm") version "1.7.20"
}

group = property("maven_group")!!
version = property("mod_version")!!

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin="org.jetbrains.kotlin.jvm")

    repositories {
        maven("https://libraries.minecraft.net")
    }
}