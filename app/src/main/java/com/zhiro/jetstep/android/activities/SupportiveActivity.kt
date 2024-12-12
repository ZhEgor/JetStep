package com.zhiro.jetstep.android.activities

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhiro.jetstep.android.services.AccelerometerCollectorService
import com.zhiro.jetstep.android.services.latestValue
import com.zhiro.jetstep.core.design.system.theme.JetStepTheme
import com.zhiro.jetstep.domain.model.RawAccelerometerRecord
import com.zhiro.jetstep.domain.repository.SensorRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SupportiveActivity : ComponentActivity() {

    private val sensorRepository: SensorRepository by inject()
    private val accelerometerRecordsFlow = sensorRepository.getAllRawAccelerometerRecordsFlow()
        .map(List<RawAccelerometerRecord>::reversed)
    private var accelerometerCollectorService: AccelerometerCollectorService? = null
    var bound by mutableStateOf(false)
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance.
            val binder = service as AccelerometerCollectorService.AccelerometerCollectorBinder
            accelerometerCollectorService = binder.getService()
            bound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            bound = false
            accelerometerCollectorService?.stopSelf()
            accelerometerCollectorService = null
        }

        override fun onBindingDied(name: ComponentName?) {
            super.onBindingDied(name)
            bound = false
        }

        override fun onNullBinding(name: ComponentName?) {
            super.onNullBinding(name)
            bound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val accelerometerRecords by accelerometerRecordsFlow.collectAsState(
                emptyList(),
                Dispatchers.IO
            )
            val scope = rememberCoroutineScope()

            JetStepTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "latest value: ${latestValue.value - 9.8}", fontSize = 28.sp)

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                bindService(
                                    Intent(
                                        this@SupportiveActivity,
                                        AccelerometerCollectorService::class.java
                                    ).apply {
                                        putExtra(AccelerometerCollectorService.PACE_ARG, "walking")
                                    },
                                    connection,
                                    Context.BIND_AUTO_CREATE
                                )
                            },
                            enabled = !bound
                        ) {
                            Text(text = "Start collecting walking")
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                bindService(
                                    Intent(
                                        this@SupportiveActivity,
                                        AccelerometerCollectorService::class.java
                                    ).apply {
                                        putExtra(AccelerometerCollectorService.PACE_ARG, "running")
                                    },
                                    connection,
                                    Context.BIND_AUTO_CREATE
                                )
                            },
                            enabled = !bound
                        ) {
                            Text(text = "Start collecting running")
                        }
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                bindService(
                                    Intent(
                                        this@SupportiveActivity,
                                        AccelerometerCollectorService::class.java
                                    ).apply {
                                        putExtra(AccelerometerCollectorService.PACE_ARG, "jogging")
                                    },
                                    connection,
                                    Context.BIND_AUTO_CREATE
                                )
                            },
                            enabled = !bound
                        ) {
                            Text(text = "Start collecting jogging")
                        }

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    accelerometerCollectorService?.finish()
                                    unbindService(connection)
                                    bound = false
                                }
                            },
                            enabled = bound
                        ) {
                            Text(text = "Stop collecting")
                        }

                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                scope.launch(Dispatchers.IO) {
                                    sensorRepository.deleteAllData()
                                }
                            },
                        ) {
                            Text(text = "Delete data")
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(
                                accelerometerRecords,
                                key = { item -> item.recordTakenAt }
                            ) { item ->
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Text("pace: ${item.pace}:\t")
                                    Text("z: ${item.z}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
