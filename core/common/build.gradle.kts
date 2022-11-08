plugins {
    id("learnbasque.android.library")
    id("learnbasque.android.hilt")
}

android {
    namespace = "com.renaudfavier.learnbasque.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}
