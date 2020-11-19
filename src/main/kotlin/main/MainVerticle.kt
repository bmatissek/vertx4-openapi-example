package main

import io.vertx.core.http.HttpServerOptions
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import rest.SimpleController

@SuppressWarnings("unused")
class MainVerticle : CoroutineVerticle() {

    override suspend fun start() {

        val openApiSpec = "src/main/resources/openapi-webapi-definition.yaml"
        val simpleController = SimpleController(vertx, openApiSpec)
        val simpleApiRouter = simpleController.createRouter()

        val httpServerOptions = HttpServerOptions()
            .setPort(8080)
            .setHost("localhost")
        val server = vertx.createHttpServer(httpServerOptions)
        server.requestHandler(simpleApiRouter).listen().await()
    }
}
