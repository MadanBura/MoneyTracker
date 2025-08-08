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
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/NOTICE.md",
                "META-INF/DEPENDENCIES",
                "META-INF/AL2.0",
                "META-INF/LGPL2.1"
            )
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.ads.mobile.sdk)
    implementation(libs.androidx.ui.test.junit4.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.material)

    //for system status bar

//    implementation("com.google.accompanist:accompanist-systemuicontroller:0.34.0")
//    Navigation
    val nav_version = "2.9.0"
    implementation(libs.androidx.navigation.compose)

//    DaggerHilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)


    //    Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //    Room Dependency
    val room_version = "2.6.1"
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)


//    Coroutine
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)


//    LifeCycle
    val lifecycle_version = "2.8.6"
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

//    Glide
    implementation(libs.glide)

//    Coil
    implementation(libs.coil.compose)

//    Material Icon
    implementation(libs.androidx.material.icons.extended)


    implementation(libs.coil.gif)


//    Google Fonts
    implementation(libs.androidx.ui.text.google.fonts)

    implementation (libs.maps.compose)
    implementation (libs.play.services.maps)
    implementation(libs.play.services.location)


    implementation(libs.androidx.core.splashscreen.v100)

    implementation(libs.androidx.material)

    implementation(platform(libs.androidx.compose.bom.v20240500))
    implementation(libs.androidx.compose.material3.material3)

    implementation(libs.androidx.compose.material3.material3)


    implementation(libs.androidx.foundation)

    implementation(libs.mpandroidchart)

    // Unit Testing
    androidTestImplementation("junit:junit:4.13.2")
    testImplementation("junit:junit:4.13.2")

    // Coroutine Test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")

    // Mocking
    testImplementation("io.mockk:mockk:1.13.5")
    androidTestImplementation("io.mockk:mockk:1.13.5")

    // For using Truth assertions (optional)
    testImplementation("com.google.truth:truth:1.1.5")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.8.3")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.8.3")

    testImplementation("org.robolectric:robolectric:4.13")

    androidTestImplementation("org.robolectric:robolectric:4.13")


    testImplementation("com.google.dagger:hilt-android-testing:2.48")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.48")

    testImplementation("junit:junit:4.13.2")
    testImplementation("org.robolectric:robolectric:4.11.1")

    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.1")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.1")
    testImplementation("io.mockk:mockk:1.13.5")


}