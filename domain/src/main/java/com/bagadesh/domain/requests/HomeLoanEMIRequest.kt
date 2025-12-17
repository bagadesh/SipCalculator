package com.bagadesh.domain.requests

data class HomeLoanEMIRequest(
    val principal: Double,
    val interestRate: Double,
    val tenureYears: Int,
    val inflationRate: Double,
    val inflationYears: Int,
    val currentRent: Double,
    val rentIncreaseRate: Double
)
