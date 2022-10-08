package com.bagadesh.sipcalculator.entities.states

/**
 * Created by bagadesh on 19/07/22.
 */
sealed class UIState<T> {

    data class Success<T>(val data: T) : UIState<T>()

    data class Failure<T>(val message: String) : UIState<T>()

    data class Empty<T>(val emptyMessage: String = "") : UIState<T>()

}

