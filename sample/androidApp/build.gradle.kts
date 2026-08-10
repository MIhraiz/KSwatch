plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "io.github.mihraiz.kswatch.sample.app"
    compileSdk = 37

    defaultConfig {
        applicationId = "io.github.mihraiz.kswatch.sample"
        minSdk = 29
        targetSdk = 37
        versionCode = 1
        versionName = "1.0.0"
    }
}

dependencies {
    implementation(project(":sample:composeApp"))
}
