import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  kotlin("multiplatform")
  kotlin("native.cocoapods")

  id("com.android.library")
  id("kotlinx-serialization")
}
group = "com.example.kmmtemplate.cocoapods"
version = "1.0-SNAPSHOT"

val coroutineVersion = "1.4.2-native-mt"
val serializationVersion = "1.0.1"
val ktorVersion = "1.5.1"

repositories {
  gradlePluginPortal()
  google()
  jcenter()
  mavenCentral()
}
kotlin {
  android()

  val iosTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
    if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
      ::iosArm64
    else
      ::iosX64

  iosTarget("ios") {}

  cocoapods {
    summary = "Kotlin sample project with CocoaPods dependencies"
    homepage = "https://github.com/Kotlin/kotlin-with-cocoapods-sample"

    ios.deploymentTarget = "13.5"

    pod("AFNetworking") { version = "~> 4.0" }
//    pod("RxSwift") { version = "~> 6.0" }
//    pod("RxCocoa") { version = "~> 6.0" }
//    pod("RxRelay") { version = "~> 6.0" }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")

        implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationVersion")

        implementation("io.ktor:ktor-client-core:$ktorVersion")
        implementation("io.ktor:ktor-client-serialization:$ktorVersion")
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))
      }
    }
    val androidMain by getting {
      dependencies {
        api("io.ktor:ktor-client-okhttp:$ktorVersion")
        api("io.ktor:ktor-client-serialization-jvm:$ktorVersion")
      }
    }
    val androidTest by getting {
      dependencies {
        implementation(kotlin("test-junit"))
        implementation("junit:junit:4.13.1")
      }
    }
    val iosMain by getting
    val iosTest by getting
  }
}
android {
  compileSdkVersion(30)
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  defaultConfig {
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
