package com.bagadesh.domain.entities.sip

/**
 * Created by bagadesh on 02/08/22.
 */
data class SipResultData(
    val amount: String,
    val interest: String,
    val year: Int,
    val result: String,
    val interestEarned: String,
    val totalInvested: String,

    val interestPercentageGained: String,
)
