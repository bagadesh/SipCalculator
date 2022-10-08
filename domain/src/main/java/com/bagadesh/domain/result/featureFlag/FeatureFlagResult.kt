package com.bagadesh.domain.result.featureFlag

/**
 * Created by bagadesh on 11/08/22.
 */
data class FeatureFlagResult(
    val featureFlagKey: FeatureFlagKey,
    val enabled: Boolean
)

@JvmInline
value class FeatureFlagKey(val value: String)

