plugins {
    alias(libs.plugins.jetstep.android.feature)
    alias(libs.plugins.jetstep.koin)
}

android {
    namespace = "com.zhiro.jetstep.feature.dashboard"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.onnxruntime.android)
}
