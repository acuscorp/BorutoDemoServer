package com.example.plugins

import com.example.repostory.HeroRepository
import com.example.routes.getAllHeroes
import com.example.routes.root
import com.example.routes.searchHeroes
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    val heroRepository: HeroRepository by inject()

    routing {
        root()
        getAllHeroes(heroRepository)
        searchHeroes(heroRepository)
        staticResources("/images","images")
    }
}
