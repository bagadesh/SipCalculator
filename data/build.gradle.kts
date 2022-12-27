plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 32
    defaultConfig {
        minSdk = 23
        targetSdk = 32
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFile("consumer-rules.pro")
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.2")
    implementation("com.google.android.material:material:1.6.1")

    implementation(project(":domain"))
    kapt(Dependencies.Hilt.Compiler)
    implementation(Dependencies.Hilt.Core)

    implementation(Dependencies.Persistence.DataStore)
    implementation(Dependencies.Room.Runtime)
    implementation(Dependencies.Room.Ktx)
    kapt(Dependencies.Room.Compiler)

    implementation(Dependencies.Gson.gson)
    implementation(Dependencies.Math.BigMath)


    testImplementation(Dependencies.Test.Junit)
}

tasks.withType<Test>() {
    useJUnitPlatform()
}
