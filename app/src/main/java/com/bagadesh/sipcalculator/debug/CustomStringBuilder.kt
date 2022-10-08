package com.bagadesh.sipcalculator.debug

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Created by bagadesh on 02/08/22.
 */
class CustomStringBuilder {

    private val builder: StringBuilder = StringBuilder()

    private var holisticTag = "DATMUG"

    fun appendTag(tag: String) {
        holisticTag = if (tag.isNotEmpty()) {
            "$holisticTag : $tag"
        } else {
            tag
        }
    }

    fun appendLine(value: String?) {
        builder.append("$holisticTag : $value").appendLine()
    }

    override fun toString(): String {
        return builder.toString()
    }

}

@OptIn(ExperimentalContracts::class)
public inline fun buildCustomString(builderAction: CustomStringBuilder.() -> Unit): String {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return CustomStringBuilder().apply(builderAction).toString()
}