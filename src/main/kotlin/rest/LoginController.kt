package rest

import io.vertx.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class LoginController(private val vertx: Vertx, private val jwtProvider: JWTAuth) : RestController {

    override suspend fun createRouter(): Router {
        val router = Router.router(vertx)
        router.get().handler(::loginHandler)
        return router
    }

    private fun loginHandler(rc: RoutingContext) {
        val userClaims = JsonObject().put("foo", "bar")
        val generatedToken = jwtProvider.generateToken(userClaims)
        rc.response().setStatusCode(200).end(generatedToken)
    }

}