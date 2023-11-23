package com.example.routes

import com.example.repostory.HeroRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.searchHeroes(repository: HeroRepository) {
    get ("/boruto/heroes/search"){
        val name = call.request.queryParameters["name"].toString()?:""
        val heroes = repository.searchHeroes(name)

        call.respond(
            message = heroes,
            status = HttpStatusCode.OK
        )

    }
}