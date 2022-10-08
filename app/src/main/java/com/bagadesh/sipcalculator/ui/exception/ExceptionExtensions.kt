package com.bagadesh.sipcalculator.ui.exception

import com.bagadesh.sipcalculator.BuildConfig

/**
 * Created by bagadesh on 05/08/22.
 */

fun Exception.convertToUIMessage(): String {
    return if (BuildConfig.DEBUG) {
        stackTraceToString()
    } else {
        message.orEmpty()
    }
}