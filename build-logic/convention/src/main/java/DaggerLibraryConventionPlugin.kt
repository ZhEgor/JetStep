import com.zhiro.jetstep.build.logic.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class DaggerLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                add("implementation", libs.findLibrary("dagger").get())
            }
        }
    }
}