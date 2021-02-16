buildscript {
  repositories {
    gradlePluginPortal()
    jcenter()
    google()
    mavenCentral()
  }
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
    classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.30")
    classpath("com.android.tools.build:gradle:4.1.2")
    classpath("com.vanniktech:gradle-maven-publish-plugin:0.11.1")
  }
}
group = "com.example.kmmtemplate"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}
