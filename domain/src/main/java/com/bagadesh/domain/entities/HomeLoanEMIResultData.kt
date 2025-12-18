package com.bagadesh.domain.entities

data class HomeLoanEMIResultData(
    val emi: String,
    val totalInterest: String,
    val totalPayment: String,
    val inflationAdjustedEMI: String,
    val yearlyRentBreakdown: List<RentBreakdown>,
    val yearlyInflationBreakdown: List<InflationBreakdown>,
    val yearlySipBreakdown: List<SipBreakdown>
)

data class RentBreakdown(
    val year: Int,
    val monthlyRent: Double,
    val yearlyTotal: Double
)

data class InflationBreakdown(
    val year: Int,
    val inflationAdjustedEmi: Double
)

data class SipBreakdown(
    val year: Int,
    val investableAmountMonthly: Double,
    val totalInvested: Double,
    val projectedValue: Double
)
