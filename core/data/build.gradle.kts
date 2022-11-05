plugins {
    id("learnbasque.android.library")
}

android {
    namespace = "com.renaudfavier.learnbasque.core.data"
}

dependencies {
    implementation(project(":core:model"))

    implementation(libs.kotlinx.coroutines.android)
}
