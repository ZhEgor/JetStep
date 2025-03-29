plugins {
    alias(libs.plugins.jetstep.android.library)
    alias(libs.plugins.jetstep.koin)
}

android {
    namespace = "com.zhiro.jetstep.core.locomotion.classifier"
}

dependencies {
    implementation(projects.core.common)
    implementation(libs.onnxruntime.android)
}
