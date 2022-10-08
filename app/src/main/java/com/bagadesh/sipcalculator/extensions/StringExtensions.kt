package com.bagadesh.sipcalculator.extensions

/**
 * Created by bagadesh on 23/07/22.
 */

fun StringBuilder.appendLineTag(value: String?): StringBuilder = append("DATMUG $value").appendLine()
