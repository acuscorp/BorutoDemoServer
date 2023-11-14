package com.example.plugin

import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.setupKoin() {
    install(Koin) {
        slf4jLogger()

    }
}