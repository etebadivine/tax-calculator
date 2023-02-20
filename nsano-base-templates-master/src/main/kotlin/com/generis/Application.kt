package com.generis

import com.generis.config.Configuration
import com.generis.config.DatabaseFactory
import com.generis.config.Monitoring
import com.generis.config.plugins.configureHTTP
import com.generis.config.plugins.configureSerialization
import com.generis.config.Communication
import com.generis.config.plugins.configureDI
import com.generis.config.plugins.configureExceptions
import com.generis.config.plugins.configureRouting
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.application.Application
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.system.exitProcess


val logger: Logger = LoggerFactory.getLogger("Application.kt")
fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    Configuration.commandLineFile = environment.config.propertyOrNull("ktor.service.config")?.getString() ?: ""

    print("Service configuration file ${environment.config.propertyOrNull("ktor.service.config")}\n")

    Configuration.loadSystemProperties() //load the system properties
    DatabaseFactory.connect()
    Communication.initializeEventBus().onSuccess {

        Communication.initializeRabbitMQ()
        Communication.initializeKafka()

        //install
        configureSerialization()
        configureHTTP()
        configureDI()
        configureRouting()
        Monitoring.configureMonitoring()
        configureExceptions()
    }.onFailure {
        logger.warn("Failed to start event bus - ${it.message}")
        exitProcess(-1)
    }

    return
}
