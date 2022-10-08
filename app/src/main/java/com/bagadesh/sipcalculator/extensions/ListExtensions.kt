package com.bagadesh.sipcalculator.extensions

/**
 * Created by bagadesh on 28/07/22.
 */

inline fun <T> List<T>.forEscher(action: (index: Int, item: T, isFirst: Boolean, isLast: Boolean) -> Unit) {
    val size = size
    forEachIndexed { index, t ->
        action(index, t, index == 0, index == size - 1)
    }
}
