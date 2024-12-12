import com.zhiro.jetstep.build.logic.convention.configureKoinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidKoinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            configureKoinAndroid()
        }
    }
}
