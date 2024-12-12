import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("jetstep.android.library")
                apply("jetstep.android.koin")
                apply("jetstep.android.library.compose")
            }

            dependencies {
                add("implementation", project(":domain"))
                add("implementation", project(":core:ui"))
                add("implementation", project(":core:navigation"))
                add("implementation", project(":core:common"))

                add("testImplementation", project(":domain-test"))
                add("androidTestImplementation", project(":domain-test"))

                add("testImplementation", kotlin("test"))
                add("androidTestImplementation", kotlin("test"))
            }
        }
    }
}