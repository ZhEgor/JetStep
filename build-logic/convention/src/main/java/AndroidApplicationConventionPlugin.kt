import com.android.build.api.dsl.ApplicationExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.zhiro.jetstep.build.logic.convention.configureApkName
import com.zhiro.jetstep.build.logic.convention.configureKotlinAndroid
import com.zhiro.jetstep.build.logic.convention.configureSigning
import com.zhiro.jetstep.build.logic.convention.utils.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("jetstep.flavors")
                apply("jetstep.detekt")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
                configureSigning(this)
                defaultConfig.targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
            }

            extensions.configure<BaseAppModuleExtension> {
                configureApkName(this)
            }
        }
    }
}
