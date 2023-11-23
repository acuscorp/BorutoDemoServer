package com.example.routes


import com.example.models.ApiResponse
import com.example.repostory.HeroRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllHeroes(heroRepository: HeroRepository) {

    get("/boruto/heroes") {
        try {
            val page = call.request.queryParameters["page"]?.toInt() ?: 1
            require(page in 1..5)

            val apiResponse = heroRepository.getAllHeroes(page)

            call.respond(
                message = apiResponse,
                status = HttpStatusCode.OK
            )

        } catch (e: NumberFormatException) {
            call.respond(
                status = HttpStatusCode.BadRequest,
                message = ApiResponse(success = false, message = "Only Numbers Are Allowed")
            )
        }  catch (e: IllegalArgumentException) {
            call.respond(
                status = HttpStatusCode.NotFound,
                message = ApiResponse(success = false, message = "Heroes not found")
            )
        }

    }
}