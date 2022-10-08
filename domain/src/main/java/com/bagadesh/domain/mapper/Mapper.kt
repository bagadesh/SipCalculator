package com.bagadesh.domain.mapper

/**
 * Created by bagadesh on 21/09/22.
 */
interface Mapper<T, R> {

    fun mapFrom(data: T): R

    fun mapTo(data: R): T

}