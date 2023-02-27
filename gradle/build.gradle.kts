plugins {
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.application) apply false
    alias(libs.plugins.library) apply false
    alias(libs.plugins.devtools) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

repositories {
    mavenCentral()
    google()
}