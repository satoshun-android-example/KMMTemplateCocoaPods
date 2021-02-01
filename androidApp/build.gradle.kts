plugins {
  id("com.android.application")
  kotlin("android")
}
group = "com.example.kmmtemplate"
version = "1.0-SNAPSHOT"

repositories {
  gradlePluginPortal()
  google()
  jcenter()
  mavenCentral()
}

dependencies {
  implementation(project(":cocoapodsshared"))

  implementation("com.google.android.material:material:1.2.1")
  implementation("androidx.appcompat:appcompat:1.2.0")
  implementation("androidx.constraintlayout:constraintlayout:2.0.4")
  implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.2.0")
}

android {
  compileSdkVersion(30)
  defaultConfig {
    applicationId = "com.example.kmmtemplate.androidApp"
    minSdkVersion(24)
    targetSdkVersion(30)
    versionCode = 1
    versionName = "1.0"
  }
  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }
}
