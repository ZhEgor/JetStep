package com.zhiro.jetstep.feature.dashboard.presentation.dashboard.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.zhiro.jetstep.feature.dashboard.R
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState()
    var showTimePickerDialog by remember { mutableStateOf(false) }

    if (showTimePickerDialog) {
        TimePickerDialog(
            onDismiss = {
                showTimePickerDialog = false
            },
            onConfirm = { timePickerState ->
                val calendar = Calendar.getInstance()
                calendar.time =
                    Date(datePickerState.selectedDateMillis ?: calendar.timeInMillis)
                calendar.set(Calendar.HOUR, timePickerState.hour)
                calendar.set(Calendar.MINUTE, timePickerState.minute)
                onDateSelected.invoke(calendar.time.time)
                showTimePickerDialog = false
            }
        )
    }

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    showTimePickerDialog = true
                }
            ) {
                Text(stringResource(R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
