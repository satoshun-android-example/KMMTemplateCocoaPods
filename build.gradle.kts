buildscript {
  repositories {
    gradlePluginPortal()
    jcenter()
    google()
    mavenCentral()
  }
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.21")
    classpath("org.jetbrains.kotlin:kotlin-serialization:1.4.21")
    classpath("com.android.tools.build:gradle:4.1.2")
    classpath("co.touchlab:kotlinnativecocoapods:0.11")
    classpath("com.vanniktech:gradle-maven-publish-plugin:0.11.1")
  }
}
group = "com.example.kmmtemplate"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}
