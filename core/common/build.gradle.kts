plugins {
    id("learnbasque.android.library")
}

android {
    namespace = "com.renaudfavier.learnbasque.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}
