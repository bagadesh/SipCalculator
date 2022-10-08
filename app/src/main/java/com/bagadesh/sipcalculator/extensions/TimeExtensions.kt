@file:OptIn(ExperimentalTime::class)

package com.bagadesh.sipcalculator.extensions

import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource
import kotlin.time.measureTime

/**
 * Created by bagadesh on 28/07/22.
 */

inline fun <T> measureTimePrint(message: String = "", action: () -> T): T {
    val data: T
    val duration = measureTime {
        data = action()
    }
    println("DATMUG, measureTimePrint $message $duration")
    return data
}

class TimeMarker() {
    var time = TimeSource.Monotonic.markNow()
    fun elapsedNow(): Duration {
        return time.elapsedNow()
    }
}