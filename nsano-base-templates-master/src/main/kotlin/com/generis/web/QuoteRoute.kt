package com.generis.web

import com.generis.com.generis.model.CreateQuotesDto
import com.generis.com.generis.model.TaxRequest
import com.generis.controller.QuoteController
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.kodein.di.instance
import org.kodein.di.ktor.closestDI

private const val BASE_URL = "api/v1/quotes"

fun Routing.quoteRoutes(){

    route(BASE_URL){
        post {

            val taxRequest = call.receive<TaxRequest>()

            val controller by closestDI().instance<QuoteController>()
            call.respond(
                status = HttpStatusCode.Created,
                controller.create(taxRequest)
            )
        }

        get("/{id}") {
            val id = call.parameters["id"]?: throw BadRequestException(message = "Quote id is undefined")

            val quoteID= Integer.parseInt(id)
            val controller by closestDI().instance<QuoteController>()
            call.respond(
                status = HttpStatusCode.OK,
                controller.get(quoteID))

        }
    }
}
