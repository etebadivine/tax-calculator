package com.generis.com.generis.model

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class Tax (
    @Required
    var id: Int,
    @Required
    var taxableAmount: Double = 0.00,
    @Required
    var rate: Double,
    @Required
    var taxPaid: Double,
    @Required
    var quoteId: Int,
)

@Serializable
data class TaxRequest(
    @Required
    var monthlyGrossIncome: Double = 0.00,
    @Required
    var monthlyAllowance: Double,
    @Required
    var taxRelief: Double,
)


@Serializable
data class CreateTaxDto (
    var id: Int,
    @Required
    var taxableAmount: Double = 0.00,
    @Required
    var rate: Double,
    @Required
    var taxPaid: Double,
    @Required
    var quoteId: Int,
)



