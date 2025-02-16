plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
}

android {
    namespace = "me.maxhub.hercules.mvi"
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
    implementation(libs.androidx.navigation.compose)
    implementation(libs.runtime.compose)
    implementation(libs.runtime.viewmodel)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.compose)
}