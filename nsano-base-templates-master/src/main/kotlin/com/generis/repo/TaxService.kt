package com.generis.repo

import com.generis.com.generis.model.CreateTaxDto
import com.generis.com.generis.repo.Taxes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TaxService : TaxesDAO {
    override fun add(taxes:List<CreateTaxDto>, quoteId:Int): ResultRow? = transaction {
        Taxes.batchInsert(taxes) {
            this[Taxes.taxableAmount] = it.taxableAmount
            this[Taxes.rate] = it.rate
            this[Taxes.taxPaid] = it.taxPaid
            this[Taxes.quoteId] = quoteId

        }.singleOrNull()
    }


    override fun close() {
        TODO("Not yet implemented")
    }

}