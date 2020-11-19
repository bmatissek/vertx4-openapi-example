package rest

import io.vertx.core.Vertx
import io.vertx.ext.web.*
import io.vertx.ext.web.openapi.RouterBuilder
import io.vertx.ext.web.validation.*
import io.vertx.kotlin.coroutines.await

class SimpleController(private val vertx: Vertx, private val openApiSpecPath: String) {

    suspend fun createRouter(): Router {
        val routerBuilder = RouterBuilder.create(vertx, openApiSpecPath).await()
        routerBuilder.operation("getSomeStuff")
                .handler(::getSomeStuff)
        return routerBuilder.createRouter()
    }

    private fun getSomeStuff(rc: RoutingContext) {
        val requestParameters = rc.get<RequestParameters>(ValidationHandler.REQUEST_CONTEXT_KEY)
        val myPathParam = requestParameters.pathParameter("someId").string
        rc.end(myPathParam)
    }
}