plugins {
    alias(libs.plugins.jetstep.android.library)
    alias(libs.plugins.jetstep.android.library.compose)
}

android {
    namespace = "com.zhiro.jetstep.core.ui"
}

dependencies {
    implementation(projects.domainModel)
    implementation(projects.core.common)
    api(projects.core.designSystem)
}
