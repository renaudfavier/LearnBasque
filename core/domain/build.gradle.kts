plugins {
    id("learnbasque.android.library")
}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
}
