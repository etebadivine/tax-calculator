package com.generis.controller

import com.generis.com.generis.model.QuotesResponseDto
import com.generis.com.generis.model.TaxRequest
import com.generis.model.APIResponse

interface QuoteController {
    /**
     * Add Quote
     * */
    fun create(taxRequest: TaxRequest): APIResponse<QuotesResponseDto>

    /**
     * Get one Quote by id
     * */
    fun get(id: Int): APIResponse<List<QuotesResponseDto>>

}