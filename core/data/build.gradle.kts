plugins {
    id("learnbasque.android.library")
    id("learnbasque.android.hilt")
}

android {
    namespace = "com.renaudfavier.learnbasque.core.data"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:model"))
    implementation(project(":core:database"))

    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.coroutines.android)
}
