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
    val focusManager = LocalFocusManager.current
    var investmentType by homeViewModel.investmentType
    var saveResultsCurrentData: SaveResultsCurrentData by remember { mutableStateOf(SaveResultsCurrentData.Empty) }
    val systemUIController = rememberSystemUiController()
    systemUIController.setStatusBarColor(color = Color.Transparent)
    systemUIController.isStatusBarVisible = false


    val bottomSheetScaffoldState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            focusManager.clearFocus()
            return@rememberModalBottomSheetState true
        })
    val scope = rememberCoroutineScope()

    val onSaveClick = { result: SaveResultsCurrentData ->
        saveResultsCurrentData = result
        scope.launch {
            if (bottomSheetScaffoldState.isVisible) {
                bottomSheetScaffoldState.hide()
            } else {
                bottomSheetScaffoldState.show()
            }
        }
        Unit
    }

    ModalBottomSheetLayout(
        sheetContent = {
            SaveResultsUI(saveResultsCurrentData, homeViewModel) {
                scope.launch {
                    bottomSheetScaffoldState.hide()
                }
            }
        },
        sheetContentColor = Color.Transparent,
        sheetElevation = 10.dp,
        sheetBackgroundColor = Color.Transparent,
        scrimColor = Color.Black.copy(alpha = 0.8f),
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
        InvestmentType.EXPERIMENT -> ExperimentUI()
    }
}

@Composable
fun SaveResultsUI(
    saveResultsCurrentData: SaveResultsCurrentData, homeViewModel: HomeViewModel, onClick: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var title by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(0.dp)
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .fillMaxWidth()
            .wrapContentSize()
            .height(intrinsicSize = IntrinsicSize.Min)
            .background(color = MaterialTheme.colors.background)
            .clickable {
                focusManager.clearFocus()
            }
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Title",
            modifier = Modifier.padding(0.dp),
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold
        )
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth(),
            isError = isError,
            placeholder = {
                Text(text = "ex: Retirement", color = LocalTextStyle.current.color.copy(alpha = .2f))
            }
        )
        Button(
            onClick = {
                if (title.isEmpty()) {
                    isError = true
                    return@Button
                }
                when (saveResultsCurrentData) {
                    SaveResultsCurrentData.Empty -> {
                        isError = true
                    }
                    is SaveResultsCurrentData.Success -> {
                        focusManager.clearFocus()
                        homeViewModel.saveResults(
                            title = title,
                            investmentType = saveResultsCurrentData.investmentType,
                            investmentDetails = saveResultsCurrentData.investDetails
                        )
                        isError = false
                        title = ""
                        onClick()
                    }
                }

            },
            modifier = Modifier
                .padding(0.dp)
                .clip(RoundedCornerShape(10.dp))
                .fillMaxWidth()
                .height(60.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Save", color = buttonTextColor)
        }
    }
}


