package com.generis.com.generis.repo

import com.generis.com.generis.model.QuotesResponseDto
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select


object Quotes : Table(name = "quotes") {
    val id: Column<Int> = integer("id").autoIncrement().uniqueIndex()
    val monthlyNetIncome: Column<Double> = double("monthlyNetIncome").default(0.00)
    val incomeTax: Column<Double> = double("incomeTax").default(0.00)
    val ssnit: Column<Double> = double("ssnit").default(0.00)

    override val primaryKey = PrimaryKey(id, name = "PK_quotes_ID")

    fun toQuotes(row: ResultRow): QuotesResponseDto =
        QuotesResponseDto(
            id = row[id],
            monthlyNetIncome = row[monthlyNetIncome],
            incomeTax = row[incomeTax],
            ssnit = row[ssnit],
            taxes = Taxes.select{ Taxes.quoteId eq row[id]}.map { Taxes.toTaxes(it) }
        )

}

