import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
  kotlin("multiplatform")

  id("com.android.library")
  id("kotlin-android-extensions")
  id("kotlinx-serialization")

  id("com.vanniktech.maven.publish")
}
group = "com.example.kmmtemplate"
version = "1.0-SNAPSHOT"

val coroutineVersion = "1.4.1"
val serializationVersion = "1.0.1"
val ktorVersion = "1.4.1"
val datetimeVersion = "0.1.1"

repositories {
  gradlePluginPortal()
  google()
  jcenter()
  mavenCentral()
  maven(url = "https://kotlin.bintray.com/kotlinx/")
}
kotlin {
  android()
  ios {
    binaries {
      framework {
        baseName = "shared"
      }
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

//        implementation("org.jetbrains.kotlinx:kotlinx-datetime:$datetimeVersion")
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
val packForXcode by tasks.creating(Sync::class) {
  group = "build"
  val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
  val sdkName = System.getenv("SDK_NAME") ?: "iphonesimulator"
  val targetName = "ios" + if (sdkName.startsWith("iphoneos")) "Arm64" else "X64"
  val framework =
    kotlin.targets.getByName<KotlinNativeTarget>(targetName).binaries.getFramework(mode)
  inputs.property("mode", mode)
  dependsOn(framework.linkTask)
  val targetDir = File(buildDir, "xcode-frameworks")
  from({ framework.outputDirectory })
  into(targetDir)
}
tasks.getByName("build").dependsOn(packForXcode)
