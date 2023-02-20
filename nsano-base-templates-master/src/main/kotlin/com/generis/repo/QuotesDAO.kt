package com.generis.repo

import com.generis.com.generis.model.CreateQuotesDto
import com.generis.com.generis.model.QuotesResponseDto
import java.io.Closeable

interface QuotesDAO: Closeable {
    fun add(createQuotesDto: CreateQuotesDto): Int
    fun get(id: Int): QuotesResponseDto?
}