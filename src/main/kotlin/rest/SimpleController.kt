package rest

import application.InputDto
import application.SomeApplicationServiceException
import application.SomeApplicationServiceImpl
import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.JWTAuthHandler
import io.vertx.ext.web.openapi.RouterBuilder
import io.vertx.ext.web.validation.RequestParameters
import io.vertx.ext.web.validation.ValidationHandler
import io.vertx.kotlin.coroutines.await

class SimpleController(
    private val vertx: Vertx,
    private val openApiSpecPath: String,
    private val jwtAuthProvider: JWTAuth
) : RestController {

    private val someApplicationService = SomeApplicationServiceImpl()

    override suspend fun createRouter(): Router {
        val routerBuilder = RouterBuilder.create(vertx, openApiSpecPath).await()

        val jwtAuthHandler = JWTAuthHandler.create(jwtAuthProvider)

        routerBuilder.securityHandler("jwtAuth", jwtAuthHandler)

        routerBuilder.operation("postSomeStuff")
            .handler(::postSomeStuffHandler)

        routerBuilder.operation("uploadImages")
            .handler(::uploadImagesHandler)

        val router = routerBuilder.createRouter()
        router.route().failureHandler(::globalFailureHandler)
        return router
    }

    private fun postSomeStuffHandler(rc: RoutingContext) {
        val params: RequestParameters = rc.get(ValidationHandler.REQUEST_CONTEXT_KEY)
        val body = params.body()

        val inputJson = body.jsonObject
        val inputDto = InputDto(
            field1 = inputJson.getString("field1"),
            field2 = inputJson.getInteger("field2")
        )

        val outputDto = someApplicationService.doSomething(inputDto)
        val outputJson = JsonObject().put("uniqueField", outputDto.uniqueField)

        rc.response().setStatusCode(200).end(outputJson.toBuffer())
    }

    private fun uploadImagesHandler(rc: RoutingContext) {
        rc.response().setStatusCode(200).end()
    }

    private fun globalFailureHandler(rc: RoutingContext) {
        when (rc.failure()) {
            is SomeApplicationServiceException -> rc.response().setStatusCode(420).end()
            else -> rc.next()
        }
    }

}