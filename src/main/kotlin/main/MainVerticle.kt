package main

import io.vertx.core.http.HttpServerOptions
import io.vertx.ext.web.Router
import io.vertx.kotlin.coroutines.CoroutineVerticle
import io.vertx.kotlin.coroutines.await
import jwt.misteriousJwtProvider
import rest.LoginController
import rest.SimpleController

@SuppressWarnings("unused")
class MainVerticle : CoroutineVerticle() {

    override suspend fun start() {

        val jwtAuthProvider = misteriousJwtProvider(vertx)

        val mainRouter = Router.router(vertx)

        val loginController = LoginController(vertx, jwtAuthProvider)
        val loginRouter = loginController.createRouter()
        mainRouter.mountSubRouter("/api/login", loginRouter)


        val openApiSpec = "src/main/resources/openapi-webapi-definition.yaml"
        val simpleController = SimpleController(vertx, openApiSpec, jwtAuthProvider)
        val simpleApiRouter = simpleController.createRouter()
        mainRouter.mountSubRouter("/api", simpleApiRouter)

        val httpServerOptions = HttpServerOptions()
            .setPort(8080)
            .setHost("localhost")
        val server = vertx.createHttpServer(httpServerOptions)
        server.requestHandler(mainRouter).listen().await()
    }
}
