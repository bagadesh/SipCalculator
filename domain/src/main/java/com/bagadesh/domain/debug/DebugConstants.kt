package com.bagadesh.domain.debug

/**
 * Created by bagadesh on 20/08/22.
 */
const val GLOBAL_ENABLE_DEBUG = true

inline fun customDebugValue(action: CustomStringBuilder.() -> Unit) {
    if (GLOBAL_ENABLE_DEBUG) {
        val value = buildCustomString(action)
        println(value)
    }
}
