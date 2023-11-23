package com.example.plugins


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPage() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound){call, statusCode ->
            call.respond(
                message = "Page not found",
                status = statusCode
            )
        }
//        exception<AuthenticationException>{ call, authenticationException ->
//            call.respond(
//                message = "We caught and exception: $authenticationException",
//                status = HttpStatusCode.OK
//            )
//        }
    }

}