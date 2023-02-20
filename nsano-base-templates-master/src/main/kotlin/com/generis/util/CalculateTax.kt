package com.generis.util

import com.generis.com.generis.model.CreateQuotesDto
import com.generis.com.generis.model.CreateTaxDto
import com.generis.com.generis.model.TaxRequest

fun calculateTax(taxRequest: TaxRequest) : CreateQuotesDto {

    val ssnit = (5.5 / 100) * taxRequest.monthlyGrossIncome;

    val taxableAmount = ((taxRequest.monthlyGrossIncome
            + taxRequest.monthlyAllowance) - ssnit) - taxRequest.taxRelief


    val chargeableIncome = arrayOf(365.0, 110.0, 130.0, 3000.0, 16395.0, 20000.0)
    val rate = arrayOf(0.0, 0.05, 0.1, 0.175, 0.25, 0.3)
    var taxes = mutableListOf<CreateTaxDto>()

    var remainder = taxableAmount
    var incomeTax = 0.0

    var count = 0
    while (count < chargeableIncome.size) {

        if (remainder < chargeableIncome[count]) {
            incomeTax += (remainder * rate[count]);
            taxes.add(
                CreateTaxDto(
                id = count,
                taxableAmount = remainder,
                rate = rate[count],
                taxPaid = remainder * rate[count],
                    quoteId = count
            ))
            remainder = 0.0;
            count += 1
        } else {
            incomeTax += (chargeableIncome[count] * rate[count]);
            remainder -= chargeableIncome[count];
            taxes.add(CreateTaxDto(
                id = count,
                taxableAmount = chargeableIncome[count],
                rate = rate[count],
                taxPaid = incomeTax,
                        quoteId = count
            )
            )
            count += 1
        }
    }

    val monthlyNetIncome = ((taxRequest.monthlyGrossIncome + taxRequest.monthlyAllowance)
            - ssnit) - incomeTax

    return CreateQuotesDto(
        monthlyNetIncome = monthlyNetIncome,
        incomeTax = incomeTax,
        ssnit = ssnit,
        taxes = taxes
    )
}