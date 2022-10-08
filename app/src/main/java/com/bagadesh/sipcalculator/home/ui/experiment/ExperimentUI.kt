package com.bagadesh.sipcalculator.home.ui.experiment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bagadesh.sipcalculator.savedResults.ui.provideColor

/**
 * Created by bagadesh on 31/08/22.
 */
@Preview
@Composable
fun ExperimentUI() {

    var expanded  by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.background(Color.LightGray)) {
        Row {
            Box(
                modifier = Modifier
                    .padding(start = 5.dp)
                    .width(5.dp)
                    .fillMaxHeight()
                    .background(provideColor(1).copy(alpha = 0.9f))
            )
            Column {
                Button(onClick = {
                    expanded = !expanded
                }) {
                    Text(text = "TestItem")
                }
                Button(onClick = { }) {
                    Text(text = "TestItem2")
                }
            }
        }
        AnimatedVisibility(visible = expanded) {
            Button(onClick = { }) {
                Text(text = "TestItem")
            }
            Button(onClick = { }) {
                Text(text = "TestItem2")
            }
        }

    }





}