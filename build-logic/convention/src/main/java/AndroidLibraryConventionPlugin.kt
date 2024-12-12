import com.android.build.api.dsl.LibraryExtension
import com.zhiro.jetstep.build.logic.convention.configureFlavors
import com.zhiro.jetstep.build.logic.convention.configureKotlinAndroid
import com.zhiro.jetstep.build.logic.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("jetstep.detekt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                configureFlavors(this)
            }

            dependencies {
                add("implementation", libs.findLibrary("kotlin-serialization-json").get())
                add("androidTestImplementation", kotlin("test"))
                add("testImplementation", kotlin("test"))
            }
        }
    }
}