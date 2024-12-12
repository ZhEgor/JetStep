package com.zhiro.jetstep.feature.dashboard.presentation.dashboard.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zhiro.jetstep.feature.dashboard.R

const val TIME_PATTERN = "d MMM yyyy | HH:mm"

@Composable
fun DateTimeOutlinedField(
    modifier: Modifier = Modifier,
    value: String,
    hint: String,
    onValueChange: (Long) -> Unit,
    isError: Boolean,
) {
    var showEndDateTimePickerDialog by remember { mutableStateOf(false) }
    val defaultColors = OutlinedTextFieldDefaults.colors()

    if (showEndDateTimePickerDialog) {
        DatePickerModal(
            onDateSelected = { newTimestamp ->
                onValueChange.invoke(newTimestamp)
                showEndDateTimePickerDialog = false
            },
            onDismiss = {
                showEndDateTimePickerDialog = false
            }
        )
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 52.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                showEndDateTimePickerDialog = true
            },
        value = value,
        onValueChange = {},
        enabled = false,
        shape = RoundedCornerShape(12.dp),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 19.sp
        ),
        maxLines = 1,
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            disabledTextColor = defaultColors.unfocusedTextColor,
            disabledContainerColor = defaultColors.unfocusedContainerColor,
            disabledSupportingTextColor = defaultColors.unfocusedSupportingTextColor,
            disabledTrailingIconColor = defaultColors.unfocusedTrailingIconColor,
            disabledPlaceholderColor = defaultColors.unfocusedPlaceholderColor,
            disabledBorderColor = defaultColors.unfocusedTextColor
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            capitalization = KeyboardCapitalization.Words
        ),
        placeholder = {
            Text(
                text = hint,
                fontSize = 16.sp,
                lineHeight = 19.sp,
                fontWeight = FontWeight.Normal,
            )
        },
        isError = isError,
        trailingIcon = {
            IconButton(onClick = {
                showEndDateTimePickerDialog = true
            }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_calendar_month),
                    contentDescription = hint,
                )
            }
        }
    )
}