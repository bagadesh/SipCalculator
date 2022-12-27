package com.bagadesh.sipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bagadesh.sipcalculator.debug.ENABLE_V2
import com.bagadesh.sipcalculator.home.ui.HomeUI
import com.bagadesh.sipcalculator.main.MainUI
import com.bagadesh.sipcalculator.ui.splash.SplashScreenController
import com.bagadesh.sipcalculator.ui.splash.setupCustomSplashFinisher
import com.bagadesh.sipcalculator.ui.theme.SipCalculatorTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setupCustomSplashFinisher()
        super.onCreate(savedInstanceState)
        setContent {
            SipCalculatorTheme {
                MainUI()
            }
        }
    }
}