plugins {
  kotlin("multiplatform")

  id("com.android.library")
  id("kotlin-android-extensions")
  id("kotlinx-serialization")

  id("co.touchlab.native.cocoapods")
}
group = "com.example.kmmtemplate.cocoapods"
version = "1.0-SNAPSHOT"

val coroutineVersion = "1.4.0"
val serializationVersion = "1.0.1"
val ktorVersion = "1.4.1"

repositories {
  gradlePluginPortal()
  google()
  jcenter()
  mavenCentral()
}
kotlin {
  android()

  val onPhone = System.getenv("SDK_NAME")?.startsWith("iphoneos") ?: false
  if (onPhone) {
    iosArm64("ios")
  } else {
    iosX64("ios")
  }
  cocoapodsext {
    summary = "Common library"
    homepage = "http://satoshun.github.io/"
    framework {
      isStatic = false
      transitiveExport = true
    }
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
