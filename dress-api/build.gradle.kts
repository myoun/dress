plugins {
    kotlin("jvm")
}

repositories {
}

dependencies {
    api("com.google.code.gson:gson:2.10.1")
    api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    api("com.mojang:brigadier:1.0.18")
    api("net.kyori:adventure-api:4.12.0")
    api("org.slf4j:slf4j-api:2.0.6")
}