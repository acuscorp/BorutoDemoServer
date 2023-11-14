package com.example

import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.*

fun Application.showStaticContent() {
    install(CallLogging)
    routing {
        staticResources("/assets", "static") {
            preCompressed(CompressedFileType.GZIP)
        }
        get("/") {
            call.respondText { "Hello world!" }
        }
        get ("/book"){
            call.respondText { "BOOK!" }
        }
        get ("/imag"){
            call.respondText { "IMAGE!" }
        }
        get("/welcome") {
            val name = call.request.queryParameters["name"]

            call.respondHtml {
                head {
                    title { +"Custom Noe" }
                }
                body {
                    if (name.isNullOrEmpty()) {
                        h3 { +"Welcome person" }
                    } else {
                        h3 { +"Welcome, $name" }
                    }
                    p { +"Current directory is: ${System.getProperty("user.dir")}"}
                    img { src = "./assets/img.png" }
                }
            }
        }
    }
}