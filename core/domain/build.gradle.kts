import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("learnbasque.android.library")
    kotlin("kapt")
}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
}
