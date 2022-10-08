package com.bagadesh.sipcalculator.debug

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bagadesh.sipcalculator.ui.theme.MaterialColorUI
import com.bagadesh.sipcalculator.ui.theme.SipCalculatorTheme

/**
 * Created by bagadesh on 22/08/22.
 */
@Composable
fun BasePreviewUI(
    hideColorBar: Boolean = false,
    content: @Composable () -> Unit
) {
    SipCalculatorTheme {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (!hideColorBar) {
                MaterialColorUI()
            }
            content()
        }
    }

}