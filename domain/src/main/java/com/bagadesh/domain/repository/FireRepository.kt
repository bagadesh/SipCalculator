package com.bagadesh.domain.repository

import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.fire.FireResults
import com.bagadesh.domain.usecases.fire.CalculateFireRequest

/**
 * Created by bagadesh on 30/08/22.
 */
interface FireRepository {

    fun calculateCorpus(request: CalculateFireRequest): Data<FireResults>

}