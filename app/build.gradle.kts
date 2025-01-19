import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
}

android {
    namespace = "pt.ul.fc.cm.pokefit"
    compileSdk = 35

    defaultConfig {
        applicationId = "pt.ul.fc.cm.pokefit"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Get the API keys from local.properties
        val properties = Properties()
        properties.load(rootProject.file("local.properties").inputStream())

        // Set MAPS_API_KEY in BuildConfig
        buildConfigField(
            "String",
            "MAPS_API_KEY",
            "\"${properties.getProperty("MAPS_API_KEY")}\""
        )
        // Pass MAPS_API_KEY to manifest placeholder
        manifestPlaceholders["MAPS_API_KEY"] = properties.getProperty("MAPS_API_KEY")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    // Enable BuildConfig generation
    buildFeatures {
        buildConfig = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // -----------------------
    // Firebase Dependencies
    // -----------------------
    implementation(platform((libs.firebase.bom)))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.crashlytics)

    // ---------------------------------------
    // Google Authentication Dependencies
    // ---------------------------------------
    implementation(libs.google.play.services.auth)
    implementation(libs.android.credentials)
    implementation(libs.android.credentials.play.services.auth)
    implementation(libs.identity.googleid)

    // ---------------------------------------
    // Google Maps & location Dependencies
    // ---------------------------------------
    implementation(libs.android.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)

    // ---------------------------------------
    // Room Dependencies (SQLite)
    // ---------------------------------------
    implementation (libs.androidx.room.runtime)
    implementation (libs.androidx.room.ktx)
    ksp (libs.androidx.room.compiler)

    // ---------------------------------------
    // Coroutines
    // ---------------------------------------
    implementation (libs.kotlinx.coroutines.android)

    // --------------------
    // Dependency Injection
    // --------------------
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // ----------------------
    // Networking Libraries
    // ----------------------
    implementation(libs.retrofit2.retrofit)
    implementation(libs.converter.gson)

    // ---------------------------
    // Image Loading Libraries
    // ---------------------------
    implementation(libs.coil.compose)
    implementation(libs.glide)

    // ---------------------------------------
    // Navigation and Lifecycle Dependencies
    // ---------------------------------------
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // ----------------------
    // Jetpack Compose Core
    // ----------------------
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.runtime.livedata)

    // -------------------------
    // Testing Dependencies
    // -------------------------
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}