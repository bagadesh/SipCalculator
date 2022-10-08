package com.bagadesh.sipcalculator.planner

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bagadesh.sipcalculator.ui.base.Heading
import com.bagadesh.sipcalculator.ui.base.UIStatePark

/**
 * Created by bagadesh on 31/07/22.
 */

const val initialTargetAmount = "1000000"

@Composable
fun PlannerUI(
    viewModel: PlannerViewModel
) {
    val incomeSourceState by viewModel.incomeSourceState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        UIStatePark(state = incomeSourceState) { incomeSourceData ->
            incomeSourceData.listOfIncomeSources.forEach { incomeSource ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Heading(
                        title = incomeSource.person.value
                    )
                    Text(text = incomeSource.income.value)
                }
            }
        }
    }

}

