package com.zhiro.jetstep

import android.app.Application
import com.zhiro.jetstep.utils.initmanager.InitManager
import com.zhiro.jetstep.utils.initmanager.KoinInitManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initApp()
    }

    private fun initApp() {
        setOf<InitManager>(
            KoinInitManager(this)
        ).forEach(InitManager::init)
    }
}
