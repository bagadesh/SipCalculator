package com.bagadesh.sipcalculator.home.ui.result

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bagadesh.sipcalculator.debug.customDebugValue
import com.bagadesh.sipcalculator.extensions.forEscher
import com.bagadesh.sipcalculator.math.SmartMoneyRepresent
import com.bagadesh.sipcalculator.ui.currency.DisplayCurrency
import com.bagadesh.sipcalculator.ui.custom.Circle

/**
 * Created by bagadesh on 25/07/22.
 */

data class HorizontalResultData(
    val title: String,
    val value: String,
    val color: Color
)

@Composable
fun ColumnScope.ResultTitle(
    title: String,
    titleColor: Color
) {
    Text(
        text = title,
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .clickable {
                customDebugValue {
                    appendTag("ResultTitle")
                    appendLine("Me clicked")
                }
            },
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = titleColor,

        )
}


@Composable
fun HorizontalResultUI(
    list: List<HorizontalResultData>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.then(
            Modifier
                .padding(vertical = 10.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface,
                    shape = RoundedCornerShape(10.dp)
                )
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Max)
                .padding(vertical = 20.dp, horizontal = 0.dp)
        ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        list.forEscher { _, item, _, isLast ->
            HorizontalCustomAmountUI(
                displayTitle = item.title,
                value = item.value,
                color = item.color
            )
            if (!isLast) {
                VerticalLine(
                    color = Color.Red,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
private fun RowScope.HorizontalCustomAmountUI(
    value: String,
    displayTitle: String,
    color: Color
) {
    Column(
        modifier = Modifier
            .weight(0.5f)
            .fillMaxHeight()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = DisplayCurrency.display(SmartMoneyRepresent.makeItPretty(value)),
            modifier = Modifier
                .padding(start = 0.dp),
            maxLines = 1,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp)
        ) {
            Circle(
                size = 10.dp,
                modifier = Modifier
                    .padding(end = 10.dp),
                color = color
            )
            Text(
                text = displayTitle,
                modifier = Modifier.alignByBaseline(),
                fontSize = 14.sp
            )
        }
        ResultValue(
            value = value,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        )

    }
}

@Composable
fun PrettyRoundedText(
    value: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit
) {
    Text(
        text = DisplayCurrency.display(SmartMoneyRepresent.makeItPretty(value)),
        modifier = modifier,
        maxLines = 1,
        fontSize = fontSize
    )
}

@Composable
fun ResultValue(
    value: String,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 12.sp
) {
    Text(
        text = DisplayCurrency.display(SmartMoneyRepresent.makeItIntegerWithoutDecimal(value)),
        modifier = modifier,
        fontWeight = FontWeight.Bold,
        fontSize = fontSize,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}


@Composable
fun VerticalLine(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface,
) {
    Divider(
        modifier = modifier.then(
            Modifier
                .height(70.dp)
                .width(1.dp)
                .background(color)
        )
    )
}