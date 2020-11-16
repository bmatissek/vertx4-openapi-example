package jwt

import io.vertx.core.Vertx
import io.vertx.ext.auth.PubSecKeyOptions
import io.vertx.ext.auth.jwt.JWTAuth
import io.vertx.ext.auth.jwt.JWTAuthOptions

private val symetricPubSecKeyOptions = PubSecKeyOptions()
    .setAlgorithm("HS256")
    .setBuffer("super-safe-key")

fun misteriousJwtProvider(vertx: Vertx) = JWTAuth.create(
    vertx,
    JWTAuthOptions().addPubSecKey(
        symetricPubSecKeyOptions
    )
)