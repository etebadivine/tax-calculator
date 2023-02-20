package com.generis.com.generis.controller

import com.generis.com.generis.model.QuotesResponseDto
import com.generis.com.generis.model.TaxRequest
import com.generis.controller.QuoteController
import com.generis.exceptions.ServiceException
import com.generis.integrations.MailService
import com.generis.integrations.SmsService
import com.generis.model.APIResponse
import com.generis.model.integration.MailRequestDto
import com.generis.model.integration.MailResponseDto
import com.generis.model.integration.SMSRequestDto
import com.generis.model.integration.SMSResponseDto
import com.generis.repo.QuotesDAO
import com.generis.repo.TaxesDAO
import com.generis.util.calculateTax
import com.generis.util.wrapFailureInResponse
import com.generis.util.wrapSuccessInResponse
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.instance
import org.slf4j.LoggerFactory

class QuoteImpl(override val di: DI):  QuoteController, DIAware {

    private val logger = LoggerFactory.getLogger(this::class.java)
    private val quotesDAO: QuotesDAO by di.instance()
    private val taxesDAO: TaxesDAO by di.instance()
    private val mailService: MailService by di.instance()
    private val smsService: SmsService by di.instance()

    override fun create(taxRequest: TaxRequest): APIResponse<QuotesResponseDto> {
        val createQuoteDao = calculateTax(taxRequest)
        val savedQuoteId = quotesDAO.add(createQuoteDao)

        if (savedQuoteId < 1)
            throw ServiceException(-1,
                "Could not save quotes.")

        taxesDAO.add(createQuoteDao.taxes, savedQuoteId)

        val savedQuote = quotesDAO.get(savedQuoteId) ?:
        throw ServiceException(-1, "Could fetch quote after saving ")

        return wrapSuccessInResponse(savedQuote)
    }

    override fun get(id: Int): APIResponse<List<QuotesResponseDto>> {
        val quotesDTO = quotesDAO.get(id) ?: return wrapFailureInResponse("Quote not found")
        return wrapSuccessInResponse(listOf(quotesDTO))
    }

    override fun sendMail(id: Int, mailRequestDto: MailRequestDto): APIResponse<MailResponseDto> {
        val quote = quotesDAO.get(id)

        val mailRequestDto = MailRequestDto(
            subject = mailRequestDto.subject,
            message = quote.toString(),
            recipients = mailRequestDto.recipients,
            signature = mailRequestDto.signature,
            senderName = mailRequestDto.senderName,
            senderEmail = mailRequestDto.senderEmail,
            password = mailRequestDto.password,
            quoteId = mailRequestDto.quoteId
        )

        val sendMail = mailService.sendMail(mailRequestDto)

        return wrapSuccessInResponse(sendMail)
    }

    override fun sendSMS(id: Int, smsRequestDto: SMSRequestDto): APIResponse<SMSResponseDto> {
        val quote = quotesDAO.get(id)

        val smsRequest = SMSRequestDto(
            recipient = smsRequestDto.recipient,
            message = quote.toString(),
            sender = quote.toString(),
            quoteId = quote.toString()
        )

        val sendSMS = smsService.sendSms(smsRequest.message, smsRequest.recipient)

        return wrapSuccessInResponse(sendSMS)
    }

}