plugins {
    id("learnbasque.android.library")
    id("learnbasque.android.hilt")
}

android {
    namespace = "com.renaudfavier.learnbasque.core.data"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
}
