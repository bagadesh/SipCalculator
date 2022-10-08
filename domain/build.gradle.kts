plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("kotlin-kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    kapt(Dependencies.Hilt.Compiler)
    implementation(Dependencies.Hilt.Core)
    implementation(Dependencies.Coroutine.Core)
}