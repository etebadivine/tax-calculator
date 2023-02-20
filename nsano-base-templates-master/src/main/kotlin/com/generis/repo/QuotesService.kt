package com.generis.repo

import com.generis.com.generis.model.CreateQuotesDto
import com.generis.com.generis.model.QuotesResponseDto
import com.generis.com.generis.repo.Quotes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class QuotesService: QuotesDAO {
    override fun add(createQuotesDto: CreateQuotesDto): Int = transaction {
      Quotes.insert {
        it[monthlyNetIncome] = createQuotesDto.monthlyNetIncome
        it[incomeTax] = createQuotesDto.incomeTax
        it[ssnit] = createQuotesDto.ssnit

      } get Quotes.id
}

    override fun get(id: Int): QuotesResponseDto? = transaction  {
        Quotes.select{ Quotes.id eq id}.map { Quotes.toQuotes(it) }.singleOrNull()
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}