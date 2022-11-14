plugins {
    id("learnbasque.android.library")
    id("learnbasque.android.library.compose")
}

android {
    namespace = "com.google.renaudfavier.learnbasque.core.ui"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:domain"))

    implementation(libs.accompanist.flowlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.datetime)

    api(libs.androidx.compose.material3)
    debugApi(libs.androidx.compose.ui.tooling)
    api(libs.androidx.compose.ui.tooling.preview)
    api(libs.androidx.compose.ui.util)
    api(libs.androidx.compose.runtime)

}
