package com.zhiro.jetstep.datasource.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.zhiro.jetstep.datasource.room.dao.AccelerometerDao
import com.zhiro.jetstep.datasource.room.dao.StepBatchDao
import com.zhiro.jetstep.datasource.room.database.SensorDatabase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule
    get() = module {

        singleOf(::provideSensorDatabase)
        singleOf(::provideAccelerometerDao)
        singleOf(::provideStepBatchDao)
        factoryOf(::provideDataStore)
    }

private fun provideSensorDatabase(context: Context): SensorDatabase {
    return Room.databaseBuilder(context, SensorDatabase::class.java, SensorDatabase.DATABASE_NAME)
        .build()
}

private fun provideAccelerometerDao(sensorDatabase: SensorDatabase): AccelerometerDao {
    return sensorDatabase.getAccelerometerDao()
}

private fun provideStepBatchDao(sensorDatabase: SensorDatabase): StepBatchDao {
    return sensorDatabase.getStepBatchDao()
}

private fun provideDataStore(applicationContext: Context) = applicationContext.dataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
