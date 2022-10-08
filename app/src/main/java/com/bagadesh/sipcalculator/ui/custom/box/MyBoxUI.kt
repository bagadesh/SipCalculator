package com.bagadesh.sipcalculator.ui.custom.box

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Created by bagadesh on 30/08/22.
 */
@Composable
fun MyBoxUI(
    first: String,
    second: String = "",
) {
    MyShapedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(1.dp, color = Color.Gray, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 1.dp
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp)
                .fillMaxWidth()
                .height(80.dp)
                .padding(0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (second.isNotEmpty()) {
                Text(
                    text = second, fontSize = 14.sp, color = MaterialTheme.colors.onSurface,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = first,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(top = 5.dp),
                color = MaterialTheme.colors.onSurface
            )
        }
    }

}

@Composable
fun MyBoxUI(
    content: @Composable () -> Unit
) {
    MyShapedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .border(1.dp, color = Color.Gray, RoundedCornerShape(10.dp)),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 1.dp
    ) {
        content()
    }
}

@Composable
fun MyShapedCard(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.medium,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp = 1.dp,
    halfArc: Color = MaterialTheme.colors.primary,
    content: @Composable () -> Unit
) {
    Card(
        modifier.drawWithContent {
            drawContent()
            val arcSize = Size(size.height / 2, size.height / 2)
            drawArc(
                color = halfArc,
                startAngle = -90f,
                sweepAngle = 180f,
                useCenter = false,
                size = arcSize,
                topLeft = Offset(-arcSize.width / 2, arcSize.height / 2)
            )
        }, shape, backgroundColor, contentColor, border, elevation
    ) {
        content()
    }
}