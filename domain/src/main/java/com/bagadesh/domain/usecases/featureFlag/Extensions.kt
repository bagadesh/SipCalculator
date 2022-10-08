package com.bagadesh.domain.usecases.featureFlag

import com.bagadesh.domain.entities.featureFlag.FeatureFlagKeys
import com.bagadesh.domain.result.Data
import com.bagadesh.domain.result.featureFlag.FeatureFlagResult
import com.bagadesh.domain.usecases.base.BaseUseCase
import kotlinx.coroutines.flow.flow

/**
 * Created by bagadesh on 11/08/22.
 */

fun BaseUseCase<FeatureFlagReaderRequest, FeatureFlagResult>.executeToFlow(param: FeatureFlagReaderRequest) = flow {
    when (val result = execute(param)) {
        is Data.Failure -> {
            emit(false)
        }
        is Data.Success -> {
            emit(result.data.enabled)
        }
    }
}