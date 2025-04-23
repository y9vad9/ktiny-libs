import org.jetbrains.kotlin.gradle.dsl.*

plugins {
    kotlin("multiplatform")
}

kotlin {
    jvm()
    jvmToolchain(11)
    js {
        browser()
        nodejs()
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    explicitApi = ExplicitApiMode.Strict
}