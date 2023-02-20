package com.generis.com.generis.controller

import com.generis.com.generis.model.QuotesResponseDto
import com.generis.com.generis.model.TaxRequest
import com.generis.controller.QuoteController
import com.generis.exceptions.ServiceException
import com.generis.model.APIResponse
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

}