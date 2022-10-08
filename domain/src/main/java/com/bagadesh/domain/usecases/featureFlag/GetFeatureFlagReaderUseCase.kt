package com.bagadesh.domain.usecases.featureFlag

import com.bagadesh.domain.repository.FeatureFlagRepository
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.featureFlag.FeatureFlagKey
import com.bagadesh.domain.result.featureFlag.FeatureFlagResult
import com.bagadesh.domain.usecases.base.BaseUseCase
import javax.inject.Inject

/**
 * Created by bagadesh on 11/08/22.
 */
class GetFeatureFlagReaderUseCase @Inject constructor(
    private val featureFlagRepository: FeatureFlagRepository
) : BaseUseCase<FeatureFlagReaderRequest, FeatureFlagResult>() {

    override suspend fun executeOnBackground(param: FeatureFlagReaderRequest): Data<FeatureFlagResult> {
        return featureFlagRepository.getFeatureFlagResult(param)
    }
}

data class FeatureFlagReaderRequest(
    val featureKey: FeatureFlagKey
)