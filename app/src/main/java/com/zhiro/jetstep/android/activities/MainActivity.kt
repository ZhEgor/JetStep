package com.zhiro.jetstep.android.activities

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.zhiro.jetstep.android.services.FitnessForegroundService
import com.zhiro.jetstep.core.design.system.theme.JetStepTheme
import com.zhiro.jetstep.core.navigation.utils.InitNavigationManager
import com.zhiro.jetstep.navigation.NavigationGraph

class MainActivity : ComponentActivity() {

    @SuppressLint("InlinedApi")
    private val permissions = setOfNotNull(
        android.Manifest.permission.POST_NOTIFICATIONS.takeIf { Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU },
        android.Manifest.permission.ACTIVITY_RECOGNITION
    )
    private val notificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            checkAndRequestNotificationPermission(
                permissions = permissions,
                onGrantedPermission = ::startBackgroundWork
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { JetStepApp() }
        checkAndRequestNotificationPermission(
            permissions = permissions,
            onGrantedPermission = ::startBackgroundWork
        )
    }

    private fun checkAndRequestNotificationPermission(
        permissions: Set<String>,
        onGrantedPermission: () -> Unit
    ) {
        val isGranted = !permissions.any { permission ->
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(permission)
                true
            } else {
                false
            }
        }
        if (isGranted) {
            onGrantedPermission.invoke()
        }
    }

    private fun startBackgroundWork() {
        if (!isServiceRunning(FitnessForegroundService::class.java.name)) {
            startForegroundService(Intent(this, FitnessForegroundService::class.java))
        }
    }

    private fun isServiceRunning(serviceName: String): Boolean {
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val services = activityManager.getRunningServices(Int.MAX_VALUE)
        return services.any { service -> service.service.className == serviceName }
    }
}

@Composable
private fun JetStepApp() {
    val navController = rememberNavController()

    JetStepTheme {
        InitNavigationManager(navController = navController)

        NavigationGraph(navController = navController)
    }
}
