plugins {
    alias(libs.plugins.jetstep.android.application)
    alias(libs.plugins.jetstep.android.application.compose)
    alias(libs.plugins.jetstep.android.koin)
}

android {
    namespace = "com.zhiro.jetstep"

    defaultConfig {
        applicationId = "com.zhiro.jetstep"
        versionCode = 1
        versionName = "0.0.1" // X.Y.Z; X = Major, Y = Minor, Z = Patch level

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.named("debug").get()
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.named("release").get()
        }
    }
}

dependencies {
    implementation(projects.domain)
    implementation(projects.core.designSystem)
    implementation(projects.core.navigation)
    implementation(projects.core.common)
    implementation(projects.feature.dashboard)
    implementation(projects.feature.locomotionclassifier)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
}
