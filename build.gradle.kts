buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath "io.realm:realm-gradle-plugin:10.11.1"
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.4" apply false
}
