import org.gradle.api.Plugin
import org.gradle.api.Project

class DetektConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("io.gitlab.arturbosch.detekt")
            }
        }
    }
}