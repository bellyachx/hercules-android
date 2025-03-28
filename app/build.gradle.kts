plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kapt)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "me.maxhub.hercules"
    compileSdk = 35

    defaultConfig {
        applicationId = "me.maxhub.hercules"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        manifestPlaceholders["appAuthRedirectScheme"] = "me.maxhub.hercules"
    }
//    signingConfigs {
//        create("release") {
//            storeFile = file(System.getenv("KEYSTORE_FILE"))
//            storePassword = System.getenv("KEYSTORE_PASSWORD")
//            keyAlias = System.getenv("KEYSTORE_ALIAS")
//            keyPassword = System.getenv("KEYSTORE_PASSWORD")
//        }
//    }
    buildTypes {
        defaultConfig {
            multiDexEnabled = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
//        getByName("release") {
//            signingConfig = signingConfigs.getByName("release")
//        }
        debug {
            applicationIdSuffix = ".test"
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    hilt {
        enableAggregatingTask = false
    }
}

dependencies {
    implementation(project(":libraries:api"))
    implementation(project(":libraries:mvi"))
    implementation(project(":libraries:widgets"))

    implementation(libs.openid.appauth)
    implementation(libs.material)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.runtime.compose)
    implementation(libs.runtime.viewmodel)
    debugImplementation(libs.androidx.ui.tooling)

    implementation(libs.hilt.android)
    implementation(libs.hilt.compose)
    implementation(libs.hilt.common)
    implementation(libs.hilt.viewmodel)
    implementation(libs.hilt.savedstatehandle)
    kapt(libs.hilt.compiler)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.core)

    implementation(libs.glide.compose)
    implementation(libs.glide.integration)
    kapt(libs.glide.compiler)
}