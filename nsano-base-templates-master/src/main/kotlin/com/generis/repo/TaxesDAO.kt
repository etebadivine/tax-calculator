package com.generis.repo

import com.generis.com.generis.model.CreateTaxDto
import org.jetbrains.exposed.sql.ResultRow
import java.io.Closeable

interface TaxesDAO: Closeable {
    fun add(taxes:List<CreateTaxDto>, quoteId:Int): ResultRow?

}