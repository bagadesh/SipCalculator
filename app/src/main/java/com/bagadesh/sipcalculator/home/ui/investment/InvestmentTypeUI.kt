package com.bagadesh.sipcalculator.home.ui.investment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.sipcalculator.debug.BasePreviewUI
import com.bagadesh.sipcalculator.ui.entities.provideInvestmentTypeList

/**
 * Created by bagadesh on 25/07/22.
 */

@Preview
@Composable
fun InvestmentTypeUIPreview() {
    BasePreviewUI {
        var value by remember {
            mutableStateOf(InvestmentType.ONE_TIME)
        }
        InvestmentTypeUI(
            value,
            onValueChange = {
                value = it
            }
        )
    }

}

@Composable
fun InvestmentTypeUI(
    investmentType: InvestmentType,
    onValueChange: (InvestmentType) -> Unit
) {
    val values = remember { provideInvestmentTypeList() }
    Text(
        text = "Investment Type",
        modifier = Modifier,
        fontSize = 20.sp
    )
    LazyRow(
        modifier = Modifier
            .padding(
                top = 10.dp,
                bottom = 20.dp
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
    ) {
        items(
            values,
            key = {
                it.name
            }
        ) {
            IndividualTypeUI(
                title = it.displayValue,
                selected = investmentType == it
            ) {
                onValueChange(it)
            }
        }
    }
}

private val unSelectedColor = Color(0xFF3A3D61)

@Composable
private fun IndividualTypeUI(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val changedBackgroundColor = if (selected) {
        MaterialTheme.colors.primary
    } else {
        unSelectedColor
    }
    val shape = RoundedCornerShape(10.dp)
    Button(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .size(100.dp)
            .padding(bottom = 5.dp, start = 5.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = changedBackgroundColor
        ),
        shape = shape
    ) {
        Text(
            text = title,
            color = if (selected) {
                Color.White
            } else {
                Color(0xFF6A6D8E)
            },
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )
    }
}