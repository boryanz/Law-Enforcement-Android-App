plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlin.compose)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.ksp)
  alias(libs.plugins.google.cloud.services)
//  alias(libs.plugins.google.cloud.services) Add along with google-service.json file from Firebase
}

android {
  namespace = "com.boryanz.upszakoni"
  compileSdk = 36

  defaultConfig {
    applicationId = "com.boryanz.upszakoni"
    minSdk = 29
    targetSdk = 35
    versionCode = 16
    versionName = "1.2.7"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
  }

  buildTypes {
    release {
      isMinifyEnabled = true
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

  packaging {
    resources {
      excludes += "/META-INF/{AL2.0,LGPL2.1}"
      merges += "META-INF/LICENSE.md"
      merges += "META-INF/LICENSE-notice.md"
    }
  }

}

dependencies {
  implementation(libs.accompanist.permissions)
  implementation(libs.firebase.messaging.ktx)
  implementation(libs.firebaseFirestore)
  implementation(libs.androidx.core.splashscreen)
  implementation(libs.androidx.navigation.compose)
  implementation(libs.kotlin.stdlib)
  implementation(libs.kotlinx.serialization.json)
  implementation(libs.room.runtime)
  implementation(libs.room.ktx)
  implementation(libs.retrofit.core)
  implementation(libs.firebase.config)
  implementation(libs.firebase.analytics.ktx)
  implementation(platform(libs.firebase.bom))
  implementation(libs.firebase.ai)
//    implementation(libs.retrofit.core)
//    implementation(libs.retrofit.bom)
//    implementation(libs.coil.kt)
//    implementation(libs.coil.kt.compose)
//    implementation(libs.okhttp)
//    implementation(libs.okhttp.logging)
//    implementation(libs.koin.bom)
//    implementation(libs.koin.android)
  ksp(libs.room.compiler)
  implementation(libs.androidx.custom.tabs)
  implementation(libs.koin.androidx.compose)
  implementation(libs.androidx.compose.material)
  implementation(libs.androidx.compose.material.iconsExtended)
  implementation(libs.gson)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.lifecycle.runtime.ktx)
  implementation(libs.androidx.activity.compose)
  implementation(platform(libs.androidx.compose.bom))
  implementation(libs.androidx.ui)
  implementation(libs.androidx.ui.graphics)
  implementation(libs.androidx.ui.tooling.preview)
  implementation(libs.androidx.material3)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  implementation(libs.androidx.activity)
  implementation(libs.androidx.constraintlayout)
  implementation(libs.mockk)
  implementation(libs.retrofit.converter.jackson)
  implementation(libs.jackson.module.kotlin)testImplementation(libs.junit)
  testImplementation(libs.kotlinx.coroutines.test)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
  androidTestImplementation(platform(libs.androidx.compose.bom))
  androidTestImplementation(libs.androidx.ui.test.junit4)
  debugImplementation(libs.androidx.ui.tooling)
  debugImplementation(libs.androidx.ui.test.manifest)
}