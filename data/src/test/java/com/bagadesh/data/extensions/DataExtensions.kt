@file:OptIn(ExperimentalContracts::class)

package com.bagadesh.data.extensions

import com.bagadesh.domain.result.Data
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

/**
 * Created by bagadesh on 27/12/22.
 */

fun <T> Data<T>.isSuccess(): Boolean {

    contract {
        returns(true) implies (this@isSuccess is Data.Success)
    }

    return this is Data.Success
}

fun <T> Data<T>.isFailure(): Boolean {
    contract {
        returns(true) implies (this@isFailure is Data.Failure)
    }
    return !isSuccess()
}

fun <T> Data<T>.expect(other: Data<T>): Boolean {
    return when {
        isSuccess() && other.isSuccess() -> {
            data == other.data
        }
        isSuccess() && other.isFailure() -> {
            false
        }
        isFailure() && other.isSuccess() -> {
            false
        }
        isFailure() && other.isFailure() -> {
            exception == other.exception
        }
        else -> { false }
    }

}