package com.generis.com.generis.repo

import com.generis.com.generis.model.Tax
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table


object Taxes : Table(name = "tax") {
    val id: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val taxableAmount: Column<Double> = double("taxableAmount").default(0.00)
    val rate: Column<Double> = double("rate").default(0.00)
    val taxPaid: Column<Double> = double("taxPaid").default(0.00)
    val quoteId: Column<Int> = integer("quoteId").index()
    override val primaryKey = PrimaryKey(id, name = "PK_tax_ID")

    fun toTaxes(row: ResultRow): Tax =
        Tax(
            id = row[id],
            taxableAmount = row[taxableAmount],
            rate = row[rate],
            taxPaid = row[taxPaid],
            quoteId = row[quoteId],
        )
}