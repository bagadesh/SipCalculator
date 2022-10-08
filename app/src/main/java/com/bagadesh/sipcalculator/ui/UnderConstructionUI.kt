package com.bagadesh.sipcalculator.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bagadesh.sipcalculator.R

/**
 * Created by bagadesh on 06/08/22.
 */
@Composable
fun UnderConstructionUI(
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.then(Modifier.fillMaxSize()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_under_construction),
            modifier = Modifier.size(300.dp),
            contentDescription = "Under construction",
            tint = MaterialTheme.colors.primary
        )
        Text(
            text = title,
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}