plugins {
    alias(libs.plugins.jetstep.android.feature)
}

android {
    namespace = "com.zhiro.jetstep.feature.dashboard"
}

dependencies {
    implementation(projects.feature.locomotionclassifier)
}
