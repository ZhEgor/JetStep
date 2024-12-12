plugins {
    alias(libs.plugins.jetstep.android.library)
    alias(libs.plugins.jetstep.room.library)
    alias(libs.plugins.jetstep.koin)
}

android {
    namespace = "com.zhiro.jetstep.data"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
}
