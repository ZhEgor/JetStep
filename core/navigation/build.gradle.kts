plugins {
    alias(libs.plugins.jetstep.android.library)
    alias(libs.plugins.jetstep.android.library.compose)
    alias(libs.plugins.jetstep.android.koin)
}

android {
    namespace = "com.zhiro.jetstep.core.navigation"
}

dependencies {

    implementation(projects.core.ui)
    api(libs.androidx.navigation.compose)
}
