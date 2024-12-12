import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.zhiro.jetstep.build.logic.convention"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.androidx.room.gradlePlugin)
    compileOnly(libs.kotlin.ksp.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "jetstep.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "jetstep.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "jetstep.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "jetstep.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "jetstep.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("jvmLibrary") {
            id = "jetstep.jvm.library"
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("daggerLibrary") {
            id = "jetstep.dagger.library"
            implementationClass = "DaggerLibraryConventionPlugin"
        }
        register("koin") {
            id = "jetstep.koin"
            implementationClass = "KoinLibraryConventionPlugin"
        }
        register("androidKoin") {
            id = "jetstep.android.koin"
            implementationClass = "AndroidKoinLibraryConventionPlugin"
        }
        register("flavors") {
            id = "jetstep.flavors"
            implementationClass = "AndroidApplicationFlavorsConventionPlugin"
        }
        register("roomLibrary") {
            id = "jetstep.room.library"
            implementationClass = "RoomLibraryConventionPlugin"
        }
        register("detekt") {
            id = "jetstep.detekt"
            implementationClass = "DetektConventionPlugin"
        }
    }
}
