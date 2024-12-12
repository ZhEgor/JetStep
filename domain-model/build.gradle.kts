plugins {
    alias(libs.plugins.jetstep.android.library)
}

android {
    namespace = "com.zhiro.jetstep.domain.model"
}

dependencies {
    implementation(projects.data)

    implementation(libs.kotlin.coroutines.core)
}
