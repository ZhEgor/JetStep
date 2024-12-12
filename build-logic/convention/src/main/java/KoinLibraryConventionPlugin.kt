import com.zhiro.jetstep.build.logic.convention.configureKoin
import org.gradle.api.Plugin
import org.gradle.api.Project

class KoinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureKoin()
        }
    }
}
