@file:OptIn(ExperimentalMaterialApi::class)

package com.bagadesh.sipcalculator.home.ui.tenure

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.bagadesh.sipcalculator.ui.custom.text.MaterialCustomTextField

/**
 * Created by bagadesh on 23/07/22.
 */

@Composable
fun TenureUI(
    value: Int,
    onValueChange: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val list = remember { produceTenureYears() }
    var wrappedValue by remember { mutableStateOf(value.toString()) }

    Text(
        text = "Tenure",
        modifier = Modifier.padding(top = 10.dp),
        fontWeight = FontWeight.SemiBold
    )
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(top = 15.dp)
    ) {

        MaterialCustomTextField(
            value = wrappedValue,
            onValueChange = {
                wrappedValue = it
                onValueChange(it.toIntOrNull() ?: 0)
            },
            modifier = Modifier
                .width(50.dp)
                .height(40.dp)
                .padding(0.dp),
            contentPadding = TextFieldDefaults.textFieldWithoutLabelPadding(
                start = 15.dp,
                end = 0.dp,
                top = 5.dp,
                bottom = 0.dp,
            ),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            )
        )

        Text(
            text = "Years",
            modifier = Modifier.padding(5.dp),
        )

        LazyRow {
            items(
                items = list,
                key = {
                    it
                }
            ) {

                Button(
                    onClick = {
                        wrappedValue = it.toString()
                        onValueChange(it)
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .size(40.dp)
                        .padding(0.dp),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = it.toString(),
                        color = Color.White
                    )
                }
            }
        }
    }
}

fun produceTenureYears(): List<Int> = mutableListOf<Int>().apply {
    var i = 1
    while (i <= 50) {
        add(i)
        if (i >= 5) {
            i += 5
        } else {
            i++
        }
    }
}