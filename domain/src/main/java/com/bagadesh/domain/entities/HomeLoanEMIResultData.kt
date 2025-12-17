package com.bagadesh.domain.entities

data class HomeLoanEMIResultData(
    val emi: String,
    val totalInterest: String,
    val totalPayment: String,
    val inflationAdjustedEMI: String,
    val yearlyRentBreakdown: List<RentBreakdown>,
    val yearlyInflationBreakdown: List<InflationBreakdown>
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
