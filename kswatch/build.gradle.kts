plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.kmp.library)
    alias(libs.plugins.maven.publish)

}


kotlin {
    // JVM and Android
    jvmToolchain(11)
    android {
        namespace = "io.github.mihraiz.kswatch"
        compileSdk = 37
        minSdk = 29
    }
    jvm()
    // iOS
    iosArm64()
    iosSimulatorArm64()
    // macOS
    macosArm64()

    js(IR) {
        nodejs {
            binaries.executable() // Enables production executables for Node.js
        }
    }

    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        val commonMain by getting {
            dependencies {
                // Move Compose dependencies out of commonMain
                // since not all platforms support it
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.components.resources)
            }
        }


        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activityCompose)
                implementation(libs.compose.uitooling)
                implementation(compose.ui)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(compose.ui)
            }
        }
        val macosMain by getting {
            dependencies {
                implementation(compose.ui)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.ui)
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(libs.kotlin.node)
            }
        }


    }
}

tasks.register("prepareJsBuild") {
    dependsOn("kswatch:jsPackageJson", "kswatch:kotlinNodeJsSetup")
}
