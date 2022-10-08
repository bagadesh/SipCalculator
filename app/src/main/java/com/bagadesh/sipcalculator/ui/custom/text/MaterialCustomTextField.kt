@file:OptIn(ExperimentalMaterialApi::class)

package com.bagadesh.sipcalculator.ui.custom.text

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Created by bagadesh on 23/07/22.
 */
@Composable
fun MaterialCustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = TextFieldDefaults.textFieldWithoutLabelPadding(
        start = 8.dp, end = 8.dp
    ),
    cursorColor: Color = MaterialTheme.colors.onSurface,
    shape: Shape = RoundedCornerShape(0.dp),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    val interactionSource = remember { MutableInteractionSource() }
    // parameters below will be passed to BasicTextField for correct behavior of the text field,
    // and to the decoration box for proper styling and sizing
    val enabled = true
    val singleLine = true

    val colors = TextFieldDefaults.outlinedTextFieldColors(
        unfocusedBorderColor = MaterialTheme.colors.onSurface,
        focusedBorderColor = MaterialTheme.colors.onSurface
    )
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        // internal implementation of the BasicTextField will dispatch focus events
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine,
        textStyle = TextStyle(
            color = MaterialTheme.colors.onSurface
        ),
        cursorBrush = SolidColor(cursorColor),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions
    ) {
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            visualTransformation = VisualTransformation.None,
            innerTextField = it,
            singleLine = singleLine,
            enabled = enabled,
            // same interaction source as the one passed to BasicTextField to read focus state
            // for text field styling
            interactionSource = interactionSource,
            // keep vertical paddings but change the horizontal
            contentPadding = contentPadding,
            // update border thickness and shape
            border = {
                TextFieldDefaults.BorderBox(
                    enabled = enabled,
                    isError = false,
                    colors = colors,
                    interactionSource = interactionSource,
                    shape = shape,
                    unfocusedBorderThickness = 1.dp,
                    focusedBorderThickness = 1.dp
                )
            },
            // update border colors
            colors = colors
        )
    }
}