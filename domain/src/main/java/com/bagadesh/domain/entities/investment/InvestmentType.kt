package com.bagadesh.domain.entities.investment

/**
 * Created by bagadesh on 02/09/22.
 */
enum class InvestmentType(val displayValue: String) {


    ONE_TIME("One Time"),
    SIP("SIP"),
    SIP_THEN_ONE_TIME("SIP then One Time"),
    ONE_TIME_THEN_SIP("One Time then SIP"),
    INFLATION("Inflation"),
    CAGR("CAGR"),
    FIRE("FIRE"),
    HOME_LOAN_EMI("Home Loan EMI"),
    EXPERIMENT("Experiment");

    companion object {
        fun mapStringData(value: String): InvestmentType {
            return values().find {
                value == it.displayValue
            } ?: ONE_TIME
        }
    }


}



