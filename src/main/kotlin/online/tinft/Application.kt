package online.tinft

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
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
                },
            )
        }
        install(UserAgent) {
            agent =
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/108.0.0.0 Safari/537.36"
        }
        install(Auth) {
            bearer {
                loadTokens {
                    // Load tokens from a local storage and return them as the 'BearerTokens' instance
                    BearerTokens("08f4289a-df37-432c-822d-7cc17761e78e", "08f4289a-df37-432c-822d-7cc17761e78e")
                }
            }
        }
    }
}
