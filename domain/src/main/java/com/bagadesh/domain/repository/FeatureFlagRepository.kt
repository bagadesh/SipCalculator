package com.bagadesh.domain.repository

import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.featureFlag.FeatureFlagResult
import com.bagadesh.domain.usecases.featureFlag.FeatureFlagReaderRequest

/**
 * Created by bagadesh on 11/08/22.
 */
interface FeatureFlagRepository {

    fun getFeatureFlagResult(featureFlagReaderRequest: FeatureFlagReaderRequest): Data<FeatureFlagResult>

}