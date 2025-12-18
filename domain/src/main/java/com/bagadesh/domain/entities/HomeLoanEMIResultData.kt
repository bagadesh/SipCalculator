package com.bagadesh.domain.entities

data class HomeLoanEMIResultData(
    val emi: String,
    val totalInterest: String,
    val totalPayment: String,
    val inflationAdjustedEMI: String,
    val yearlyRentBreakdown: List<RentBreakdown>,
    val yearlyInflationBreakdown: List<InflationBreakdown>,
    val yearlySipBreakdown: List<SipBreakdown>,
    val yearlyHomePriceBreakdown: List<HomePriceBreakdown>,
    val yearlyDownPaymentBreakdown: List<DownPaymentBreakdown>,
    val yearlyRentVsEmiBreakdown: List<RentVsEmiBreakdown>
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

data class HomePriceBreakdown(
    val year: Int,
    val projectedValue: Double
)

data class DownPaymentBreakdown(
    val year: Int,
    val projectedValue: Double
)

data class RentVsEmiBreakdown(
    val year: Int,
    val monthlyExcessRent: Double,
    val projectedValue: Double
)
