plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.neo.moneytracker"
    compileSdk = 35



    defaultConfig {
        applicationId = "com.neo.moneytracker"
        minSdk = 24
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

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {

    // Core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material)
    implementation(libs.ads.mobile.sdk)
    implementation(libs.androidx.core.splashscreen)

    // Compose dependencies
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation
    val nav_version = "2.9.0"
    implementation(libs.androidx.navigation.compose)

    // Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // Room (Database)
    val room_version = "2.6.1"
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // LifeCycle components
    val lifecycle_version = "2.8.6"
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Image loading (Glide and Coil)
    implementation(libs.glide)
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)

    // Google Fonts
    implementation(libs.androidx.ui.text.google.fonts)

    // Maps
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.play.services.location)

    // MPAndroidChart (For charts)
    implementation(libs.mpandroidchart)

    // Testing dependencies
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)

    // Mocking
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.4.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-manifest:1.4.0")

    // Hilt for testing
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    androidTestImplementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // MockK for mocking
    testImplementation("io.mockk:mockk:1.12.5")
    androidTestImplementation("io.mockk:mockk-android:1.12.5")

    // JUnit for testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    // Espresso for UI Testing
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")

    // Coroutine testing dependencies
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Room for testing
    androidTestImplementation("androidx.room:room-testing:2.6.1")
    androidTestImplementation("androidx.navigation:navigation-testing:2.5.0")
    implementation("androidx.compose.material:material-icons-extended:1.1.0")
}
