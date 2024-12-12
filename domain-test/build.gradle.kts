plugins {
    alias(libs.plugins.jetstep.android.library)
}

android {
    namespace = "com.zhiro.jetstep.domain.test"
}

dependencies {
    implementation(projects.domain)
    implementation(libs.kotlin.coroutines.core)
}
