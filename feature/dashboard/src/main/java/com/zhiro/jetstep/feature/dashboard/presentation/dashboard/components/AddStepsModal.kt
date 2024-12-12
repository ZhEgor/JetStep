package com.zhiro.jetstep.feature.dashboard.presentation.dashboard.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhiro.jetstep.domain.model.StepBatch
import com.zhiro.jetstep.domain.model.StepType
import com.zhiro.jetstep.feature.dashboard.R
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddStepsModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit = {},
    onSave: (StepBatch) -> Unit = {},
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var startTimeStamp: Long? by remember { mutableStateOf(null) }
    var endTimeStamp: Long? by remember { mutableStateOf(null) }
    val startDateTime by remember(startTimeStamp) {
        val sdf = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
        mutableStateOf(
            startTimeStamp?.let { time ->
                sdf.format(Date(time))
            } ?: ""
        )
    }
    val endDateTime by remember(endTimeStamp) {
        val sdf = SimpleDateFormat(TIME_PATTERN, Locale.getDefault())
        mutableStateOf(
            endTimeStamp?.let { time ->
                sdf.format(Date(time))
            } ?: ""
        )
    }
    var stepType by remember {
        mutableStateOf(StepType.Walking)
    }
    var stepsValue by remember {
        mutableStateOf("")
    }
    val ctaEnabled by remember(startTimeStamp, endTimeStamp, stepsValue) {
        derivedStateOf {
            startTimeStamp != null && endTimeStamp != null && stepsValue.isNotEmpty()
        }
    }

    ModalBottomSheet(
        modifier = modifier
            .wrapContentHeight(),
        onDismissRequest = {
            scope.launch {
                bottomSheetState.hide()
                onDismiss()
            }
        },
        sheetState = bottomSheetState
    ) {

        Column(
            modifier = Modifier.padding(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DateTimeOutlinedField(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = startDateTime,
                hint = stringResource(R.string.pick_start_time_hint),
                onValueChange = { timestamp ->
                    startTimeStamp = timestamp
                },
                isError = false
            )

            DateTimeOutlinedField(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = endDateTime,
                hint = stringResource(R.string.pick_finish_time_hint),
                onValueChange = { timestamp ->
                    endTimeStamp = timestamp
                },
                isError = false
            )

            EnterStepsOutlinedField(
                modifier = Modifier.padding(horizontal = 16.dp),
                value = stepsValue,
                type = stepType,
                onTypeChange = { newType ->
                    stepType = newType
                },
                onValueChange = { newStepsValue ->
                    stepsValue = newStepsValue
                },
                isError = false
            )

            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(height = 52.dp),
                onClick = {
                    onSave.invoke(
                        StepBatch(
                            steps = stepsValue.toInt(),
                            startTime = startTimeStamp!!,
                            endTime = endTimeStamp!!,
                            type = stepType
                        )
                    )
                },
                enabled = ctaEnabled
            ) {
                Text(
                    text = stringResource(R.string.track),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}