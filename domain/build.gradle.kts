plugins {
    alias(libs.plugins.jetstep.android.library)
    alias(libs.plugins.jetstep.koin)
}

android {
    namespace = "com.zhiro.jetstep.domain"
}

dependencies {
    implementation(projects.data)
    api(projects.domainModel)

    implementation(libs.kotlin.coroutines.core)
}
