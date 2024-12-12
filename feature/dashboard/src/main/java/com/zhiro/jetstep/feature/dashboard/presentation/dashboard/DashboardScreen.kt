package com.zhiro.jetstep.feature.dashboard.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.components.AddStepsModal
import com.zhiro.jetstep.feature.dashboard.presentation.dashboard.components.PieChart
import com.zhiro.jetstep.feature.locomotionclassifier.secondsValue
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
internal fun DashboardScreen(viewModel: DashboardViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showAddNewStepsModal by remember { mutableStateOf(false) }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showAddNewStepsModal = true
                },
            ) {
                Icon(Icons.Outlined.Edit, "add steps manually")
            }
        }
    ) { padding ->

        if (showAddNewStepsModal) {

            AddStepsModal(
                onDismiss = {
                    showAddNewStepsModal = false
                },
                onSave = { stepBatch ->
                    viewModel.saveManually(stepBatch)
                    showAddNewStepsModal = false
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PieChart(
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(top = 32.dp)
                    .fillMaxWidth(),
                data = state.steps
            )

            Text(
                text = secondsValue.value.toString(),
                fontWeight = FontWeight.Medium,
                fontSize = 32.sp,
                color = Color.Black
            )

        }

    }
}
