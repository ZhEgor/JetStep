package com.zhiro.jetstep.feature.dashboard.presentation.dashboard.components

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.zhiro.jetstep.core.ui.utils.icon
import com.zhiro.jetstep.domain.model.StepType
import com.zhiro.jetstep.domain.model.next
import com.zhiro.jetstep.feature.dashboard.R

@Composable
fun EnterStepsOutlinedField(
    modifier: Modifier = Modifier,
    value: String,
    type: StepType,
    onValueChange: (String) -> Unit,
    onTypeChange: (StepType) -> Unit,
    isError: Boolean,
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 52.dp)
            .clip(RoundedCornerShape(12.dp)),
        value = value,
        onValueChange = { newValue ->
            if (newValue.isDigitsOnly() && newValue.length < 5) {
                onValueChange.invoke(newValue)
            }
        },
        shape = RoundedCornerShape(12.dp),
        textStyle = LocalTextStyle.current.copy(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            lineHeight = 19.sp
        ),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        placeholder = {
            Text(
                text = stringResource(R.string.enter_steps_hint),
                fontSize = 16.sp,
                lineHeight = 19.sp,
                fontWeight = FontWeight.Normal,
            )
        },
        isError = isError,
        trailingIcon = {
            IconButton(
                onClick = {
                    onTypeChange.invoke(type.next())
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = type.icon,
                    contentDescription = stringResource(R.string.enter_steps_hint),
                )
            }
        }
    )
}