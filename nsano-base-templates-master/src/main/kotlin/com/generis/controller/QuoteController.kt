package com.generis.controller

import com.generis.com.generis.model.QuotesResponseDto
import com.generis.com.generis.model.TaxRequest
import com.generis.model.APIResponse
import com.generis.model.integration.MailRequestDto
import com.generis.model.integration.MailResponseDto
import com.generis.model.integration.SMSRequestDto
import com.generis.model.integration.SMSResponseDto

interface QuoteController {
    /**
     * Add Quote
     * */
    fun create(taxRequest: TaxRequest): APIResponse<QuotesResponseDto>

    /**
     * Get one Quote by id
     * */
    fun get(id: Int): APIResponse<List<QuotesResponseDto>>

    fun sendMail(id: Int, mailRequestDto: MailRequestDto): APIResponse<MailResponseDto>
    fun sendSMS(id: Int, smsRequestDto: SMSRequestDto): APIResponse<SMSResponseDto>

}