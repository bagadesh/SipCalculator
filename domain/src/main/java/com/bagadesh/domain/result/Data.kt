package com.bagadesh.domain.result

/**
 * Created by bagadesh on 02/08/22.
 */
sealed class Data<T> {

    data class Success<T>(val data: T) : Data<T>()

    data class Failure<T>(val exception: Exception) : Data<T>()

}
