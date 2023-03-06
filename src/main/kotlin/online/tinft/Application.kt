package online.tinft

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.json.Json
import online.tinft.plugins.configureHTTP
import online.tinft.plugins.configureRouting
import online.tinft.plugins.configureSerialization

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt(), module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureHTTP()
    configureRouting(getHttpClient())
}

private fun getHttpClient(): HttpClient {
    return HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }
}