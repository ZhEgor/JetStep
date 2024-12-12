plugins {
    alias(libs.plugins.jetstep.jvm.library)
    alias(libs.plugins.jetstep.koin)
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
}
