
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    compileSdk = 32
    defaultConfig {
        minSdk = 23
        targetSdk = 32
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //consumerProguardFile("consumer-rules.pro")
        versionCode =  1
        versionName =  "1.0"
        applicationId = "com.bagadesh.sipcalculator"
        vectorDrawables {
            useSupportLibrary = true
        }
       // signingConfig = signingConfigs.getByName("releaseConfig")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs.plus("-Xjvm-default=all")
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.Version
    }
    packagingOptions {
        resources {
            resources.excludes.add("META-INF/*")
            resources.excludes.add("META-INF/**/*")
        }
    }

}


dependencies {
    val composeVersion = Dependencies.Compose.Version
    implementation ("androidx.core:core-ktx:1.8.0")
    implementation("androidx.compose.ui:ui:$composeVersion")
    implementation("androidx.compose.material:material:$composeVersion")
    implementation("androidx.compose.ui:ui-tooling-preview:$composeVersion")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation ("androidx.activity:activity-compose:1.5.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-tooling:$composeVersion")
    debugImplementation("androidx.compose.ui:ui-test-manifest:$composeVersion")

    implementation(Dependencies.Hilt.Android)
    kapt(Dependencies.Hilt.Compiler)

    implementation(project(":domain"))
    implementation(project(":data"))

    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.24.13-rc")
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    implementation("androidx.core:core-splashscreen:1.0.0-rc01")

    val nav_version ="2.5.1"
3
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.25.0")

    implementation(Dependencies.Gson.gson)
    implementation(Dependencies.Room.Runtime)
    implementation(Dependencies.Compose.ConstraintLayout)
    implementation(Dependencies.Accompanist.Pager)
    implementation(Dependencies.Accompanist.PagerIndicators)
}