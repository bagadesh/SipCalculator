package com.bagadesh.sipcalculator.ui.splash

import androidx.core.splashscreen.SplashScreen
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Created by bagadesh on 31/07/22.
 */
object SplashScreenController {

    fun homeUIDrawn() {
        splashScreenFinished.set(true)
    }

}

const val customSplashController = true
private val splashScreenFinished = AtomicBoolean(false)

fun SplashScreen.setupCustomSplashFinisher() {
    if (customSplashController) {
        setKeepOnScreenCondition(condition = SplashScreen.KeepOnScreenCondition {
            return@KeepOnScreenCondition !splashScreenFinished.get()
        })
    }
}