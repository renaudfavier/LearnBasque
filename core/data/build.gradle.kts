plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":core:model"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}
