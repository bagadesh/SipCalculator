package com.bagadesh.sipcalculator.savedResults.ui.result

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bagadesh.domain.constants.OneTimeInvestmentDetailsConstants
import com.bagadesh.domain.constants.SipThenOneTimeInvestmentDetailsConstants
import com.bagadesh.sipcalculator.R
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

/**
 * Created by bagadesh on 20/09/22.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun SipThenOneTimeResultDetailUI(details: Map<String, Any>) {
    Column(modifier = Modifier.animateContentSize()) {
        val pagerState = rememberPagerState()
        HorizontalPager(count = 2, state = pagerState) { page ->
            Column(modifier = Modifier.animateContentSize()) {
                when (page) {
                    0 -> {
                        Text(text = "Sip", modifier = Modifier.align(Alignment.CenterHorizontally), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.size(20.dp))
                        SipSavedResultDetailUI(
                            total = details[SipThenOneTimeInvestmentDetailsConstants.TOTAL].toString(),
                            totalInvested = details[SipThenOneTimeInvestmentDetailsConstants.TOTAL_INVESTED].toString(),
                            sipAmount = details[SipThenOneTimeInvestmentDetailsConstants.SIP_AMOUNT].toString(),
                            rateOfReturn = details[SipThenOneTimeInvestmentDetailsConstants.RATE_OF_RETURN].toString(),
                            tenure = details[SipThenOneTimeInvestmentDetailsConstants.TENURE].toString().toDouble().toInt().toString()
                        )
                    }
                    1 -> {
                        Text(text = "One Time ", modifier = Modifier.align(Alignment.CenterHorizontally), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        Spacer(modifier = Modifier.size(20.dp))
                        OneTimeSavedResultDetailUI(
                            total = details[SipThenOneTimeInvestmentDetailsConstants.TOTAL_FOR_ONETIME].toString(),
                            oneTimeAmount = details[SipThenOneTimeInvestmentDetailsConstants.ONETIME_AMOUNT].toString(),
                            rateOfReturn = details[SipThenOneTimeInvestmentDetailsConstants.RATE_OF_RETURN_FOR_ONETIME_AFTER_SIP].toString(),
                            tenure = details[SipThenOneTimeInvestmentDetailsConstants.TENURE_FOR_ONETIME_AFTER_SIP].toString(),
                        )
                    }
                }
            }

        }
        HorizontalPagerIndicator(pagerState = pagerState, modifier = Modifier.align(Alignment.CenterHorizontally))
    }

}