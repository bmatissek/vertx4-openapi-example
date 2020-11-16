package rest

import io.vertx.ext.web.Router

interface RestController {
    suspend fun createRouter(): Router
}