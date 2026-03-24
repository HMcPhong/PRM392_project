plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.prm392front"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.prm392front"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.inappmessaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)

    // Glide (for loading product images from URL)
    implementation(libs.glide)

    // RecyclerView & CardView
    implementation(libs.recyclerview)
    implementation(libs.cardview)

    // Standard AndroidX
    implementation(libs.appcompat.v161)
    implementation(libs.material.v1110)
    implementation(libs.constraintlayout.v214)
}