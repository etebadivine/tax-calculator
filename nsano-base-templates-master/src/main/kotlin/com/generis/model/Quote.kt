package com.generis.com.generis.model

import kotlinx.serialization.Required
import kotlinx.serialization.Serializable

@Serializable
data class Quote (
    @Required
    var id: Int,
    @Required
    var monthlyNetIncome: Double,
    @Required
    var incomeTax: Double,
    @Required
    var ssnit: Double,
)

@Serializable
data class CreateQuotesDto (
    @Required
    var monthlyNetIncome: Double,
    @Required
    var incomeTax: Double,
    @Required
    var ssnit: Double,
    @Required
    var taxes: List<CreateTaxDto>,
)


@Serializable
data class QuotesResponseDto    (
    @Required
    var id: Int,
    @Required
    var monthlyNetIncome: Double,
    @Required
    var incomeTax: Double,
    @Required
    var ssnit: Double,
    @Required
    var taxes: List<Tax>,
)

