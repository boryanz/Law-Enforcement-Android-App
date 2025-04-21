import Dependecies.ACCOMPANIST_PERMISSIONS
import Dependecies.ANDROIDX_ACTIVITY
import Dependecies.ANDROIDX_ACTIVITY_COMPOSE
import Dependecies.ANDROIDX_APPCOMPAT
import Dependecies.ANDROIDX_COMPOSE_BOM_STRING
import Dependecies.ANDROIDX_COMPOSE_MATERIAL
import Dependecies.ANDROIDX_COMPOSE_MATERIAL_ICONS_EXTENDED
import Dependecies.ANDROIDX_CONSTRAINTLAYOUT_STRING
import Dependecies.ANDROIDX_CORE_KTX
import Dependecies.ANDROIDX_CORE_SPLASHSCREEN_STRING
import Dependecies.ANDROIDX_CUSTOM_TABS
import Dependecies.ANDROIDX_ESPRESSO_CORE
import Dependecies.ANDROIDX_JUNIT
import Dependecies.ANDROIDX_LIFECYCLE_RUNTIME_KTX
import Dependecies.ANDROIDX_MATERIAL3
import Dependecies.ANDROIDX_NAVIGATION_COMPOSE
import Dependecies.ANDROIDX_UI
import Dependecies.ANDROIDX_UI_GRAPHICS
import Dependecies.ANDROIDX_UI_TEST_JUNIT4
import Dependecies.ANDROIDX_UI_TEST_MANIFEST
import Dependecies.ANDROIDX_UI_TOOLING
import Dependecies.ANDROIDX_UI_TOOLING_PREVIEW
import Dependecies.ANDROID_PDF_VIEWER
import Dependecies.FIREBASE_CONFIG_STRING
import Dependecies.FIREBASE_FIRESTORE_STRING
import Dependecies.FIREBASE_MESSAGING_KTX
import Dependecies.GSON_STRING
import Dependecies.JUNIT_STRING
import Dependecies.KOIN_ANDROIDX_COMPOSE
import Dependecies.KOTLINX_SERIALIZATION_JSON_STRING
import Dependecies.KOTLIN_STDLIB
import Dependecies.MATERIAL_STRING
import Dependecies.RETROFIT_CORE
import Dependecies.ROOM_COMPILER
import Dependecies.ROOM_KTX
import Dependecies.ROOM_RUNTIME

plugins {
    id(BuildPlugins.KOTLIN_ANDROID)
    id(BuildPlugins.ANDROID_APP)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.google.cloud.services)
}

android {
    namespace = BuildConfig.APP_ID
    compileSdk = BuildConfig.TARGET_SDK

    defaultConfig {
        applicationId = BuildConfig.APP_ID
        minSdk = BuildConfig.MIN_SDK
        targetSdk = BuildConfig.TARGET_SDK
        versionCode = ReleaseConfig.VERSION_CODE
        versionName = ReleaseConfig.VERSION_NAME

        testInstrumentationRunner = TestBuildConfig.TEST_INSTRUMENTATION_RUNNER
    }

    signingConfigs {
        BuildSigning.Debug.create(this)
        BuildSigning.Release.create(this)
    }

    buildTypes {
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = Build.Release.isMinifyEnabled
            isDebuggable = Build.Release.isDebuggable
            applicationIdSuffix = Build.Release.applicationIdSuffix
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName(SigningTypes.RELEASE)
        }

        getByName(BuildTypes.DEBUG) {
            isMinifyEnabled = Build.Debug.isMinifyEnabled
            isDebuggable = Build.Debug.isDebuggable
            applicationIdSuffix = Build.Debug.applicationIdSuffix
            signingConfig = signingConfigs.getByName(SigningTypes.DEBUG)
        }
    }
    flavorDimensions.add(BuildDimensions.APP)
    productFlavors {
        BuildFlavor.Free.create(this)
        BuildFlavor.Paid.create(this)
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
}

dependencies {
    dependencies {
        implementation(ACCOMPANIST_PERMISSIONS)
        implementation(FIREBASE_MESSAGING_KTX)
        implementation(FIREBASE_FIRESTORE_STRING)
        implementation(ANDROIDX_CORE_SPLASHSCREEN_STRING)
        implementation(ANDROIDX_NAVIGATION_COMPOSE)
        implementation(KOTLIN_STDLIB)
        implementation(KOTLINX_SERIALIZATION_JSON_STRING)
        implementation(ROOM_RUNTIME)
        implementation(ROOM_KTX)
        implementation(RETROFIT_CORE)
        implementation(FIREBASE_CONFIG_STRING)
        ksp(ROOM_COMPILER)
        implementation(ANDROID_PDF_VIEWER)
        implementation(ANDROIDX_CUSTOM_TABS)
        implementation(KOIN_ANDROIDX_COMPOSE)
        implementation(ANDROIDX_COMPOSE_MATERIAL)
        implementation(ANDROIDX_COMPOSE_MATERIAL_ICONS_EXTENDED)
        implementation(GSON_STRING)
        implementation(ANDROIDX_CORE_KTX)
        implementation(ANDROIDX_LIFECYCLE_RUNTIME_KTX)
        implementation(ANDROIDX_ACTIVITY_COMPOSE)
        implementation(platform(ANDROIDX_COMPOSE_BOM_STRING))
        implementation(ANDROIDX_UI)
        implementation(ANDROIDX_UI_GRAPHICS)
        implementation(ANDROIDX_UI_TOOLING_PREVIEW)
        implementation(ANDROIDX_MATERIAL3)
        implementation(ANDROIDX_APPCOMPAT)
        implementation(MATERIAL_STRING)
        implementation(ANDROIDX_ACTIVITY)
        implementation(ANDROIDX_CONSTRAINTLAYOUT_STRING)
    }
    testImplementation(JUNIT_STRING)
    androidTestImplementation(ANDROIDX_JUNIT)
    androidTestImplementation(ANDROIDX_ESPRESSO_CORE)
    androidTestImplementation(platform(ANDROIDX_COMPOSE_BOM_STRING))
    androidTestImplementation(ANDROIDX_UI_TEST_JUNIT4)
    debugImplementation(ANDROIDX_UI_TOOLING)
    debugImplementation(ANDROIDX_UI_TEST_MANIFEST)
}