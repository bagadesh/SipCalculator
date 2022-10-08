package com.bagadesh.data.repository

import com.bagadesh.domain.entities.featureFlag.FeatureFlagKeys
import com.bagadesh.domain.repository.FeatureFlagRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.featureFlag.FeatureFlagResult
import com.bagadesh.domain.usecases.featureFlag.FeatureFlagReaderRequest
import javax.inject.Inject

/**
 * Created by bagadesh on 11/08/22.
 */
class FeatureFlagRepositoryImpl @Inject constructor() : FeatureFlagRepository {

    override fun getFeatureFlagResult(featureFlagReaderRequest: FeatureFlagReaderRequest): Data<FeatureFlagResult> {
        return Data.Success(
            FeatureFlagResult(
                featureFlagKey = featureFlagReaderRequest.featureKey,
                enabled = when (featureFlagReaderRequest.featureKey) {
                    FeatureFlagKeys.BOND_INVESTMENT -> false
                    else -> true
                }
            )
        )
    }
}