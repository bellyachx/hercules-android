plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "me.maxhub.hercules.api"
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }
    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    // ---
    implementation(libs.retrofit.core)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinx.serialization.converter)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}