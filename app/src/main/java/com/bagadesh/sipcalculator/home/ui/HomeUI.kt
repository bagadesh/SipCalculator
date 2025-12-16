@file:OptIn(ExperimentalAnimationApi::class)

package com.bagadesh.sipcalculator.home.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bagadesh.domain.entities.investment.InvestmentType
import com.bagadesh.sipcalculator.entities.SaveResultsCurrentData
import com.bagadesh.sipcalculator.home.ui.cagr.CAGRUI
import com.bagadesh.sipcalculator.home.ui.experiment.ExperimentUI
import com.bagadesh.sipcalculator.home.ui.fire.FireUI
import com.bagadesh.sipcalculator.home.ui.inflation.InflationUI
import com.bagadesh.sipcalculator.home.ui.investment.InvestmentTypeUI
import com.bagadesh.sipcalculator.home.ui.oneTime.OneTimeUI
import com.bagadesh.sipcalculator.home.ui.sip.SipUI
import com.bagadesh.sipcalculator.home.ui.sipThenOneTime.SipThenOneTimeUI
import com.bagadesh.sipcalculator.home.viewmodel.HomeViewModel
import com.bagadesh.sipcalculator.ui.theme.buttonTextColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

/**
 * Created by bagadesh on 16/07/22.
 */

const val DefaultPrincipal = "1000"
const val DefaultRateOfInterest = 12
const val DefaultRateOfInflation = 6
const val DefaultRateOfInterestMax = 40
const val DefaultYear = 5

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeUI(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val bottomSheetScaffoldState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val coroutineScope = rememberCoroutineScope()
    var investmentType by homeViewModel.investmentType

    val onSaveClick: (SaveResultsCurrentData) -> Unit = {
        coroutineScope.launch {
            if (it is SaveResultsCurrentData.Success) {
                homeViewModel.saveResults(
                    title = "",
                    investmentType = it.investmentType,
                    investmentDetails = it.investDetails
                )
                bottomSheetScaffoldState.hide()
            }
        }
    }

    ModalBottomSheetLayout(
        sheetContent = {
            SaveResultsUI(SaveResultsCurrentData.Empty, homeViewModel) {
                coroutineScope.launch {
                    bottomSheetScaffoldState.hide()
                }
            }
        },
        sheetState = bottomSheetScaffoldState,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 0.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.padding(10.dp))
            InvestmentTypeUI(
                investmentType = investmentType,
                onValueChange = {
                    investmentType = it
                }
            )
            ContentBasedOnInvestmentType(investmentType, onSaveClick)
        }
    }
}

@Composable
fun  ContentBasedOnInvestmentType(
    investmentType: InvestmentType,
    onSaveClick: (SaveResultsCurrentData) -> Unit
) {
    when (investmentType) {
        InvestmentType.ONE_TIME -> OneTimeUI(onSaveClick = onSaveClick)
        InvestmentType.SIP -> SipUI(onSaveClick = onSaveClick)
        InvestmentType.SIP_THEN_ONE_TIME -> SipThenOneTimeUI(onSaveClick = onSaveClick)
        InvestmentType.CAGR -> CAGRUI()
        InvestmentType.FIRE -> FireUI()
        InvestmentType.INFLATION -> InflationUI()
        InvestmentType.EXPERIMENT -> ExperimentUI()
    }
}

@Composable
fun SaveResultsUI(
    saveResultsCurrentData: SaveResultsCurrentData, homeViewModel: HomeViewModel, onClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var title by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(10.dp)
            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Save Result",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = title,
            onValueChange = {
                title = it
            },
            placeholder = {
                Text(text = "Title")
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                onClick()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = buttonTextColor
            )
        ) {
            Text(text = "Save")
        }
    }
}
