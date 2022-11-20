plugins {
    id("learnbasque.android.feature")
    id("learnbasque.android.library.compose")
}

android {
    namespace = "com.renaudfavier.learnbasque.feature.microlearning"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}
