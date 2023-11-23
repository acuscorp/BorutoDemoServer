package com.example

import com.example.models.ApiResponse
import com.example.models.Hero
import com.example.repostory.HeroRepository
import com.example.repostory.HeroRepositoryImpl
import com.example.repostory.NEXT_PAGE_KEY
import com.example.repostory.PREVIOUS_PAGE_KEY
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.ktor.util.*
import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals


class ApplicationTest {
    private val heroRepository: HeroRepository = HeroRepositoryImpl()

    @Test
    fun testRootEndpoint_AssertSuccess() {
        testApplication {
            val response = client.get("/")
            assertEquals(
                expected = HttpStatusCode.OK,
                actual = response.status
            )
            assertEquals(
                expected = "Welcome to Boruto API!",
                actual = response.bodyAsText()
            )
        }
    }

    @Test
    fun `test all heroes endpoint success`() {
        testApplication {

            val response = client.get("/boruto/heroes")
            assertEquals(expected = HttpStatusCode.OK, actual = response.status)

            val expected = ApiResponse(
                success = true,
                message = "ok",
                prevPage = null,
                nextPage = 2,
                heroes = heroRepository.page1
            )

            val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText())
//
            println("EXPECTED: $expected")
            println("ACTUAL: $actual")
            assertEquals(expected, actual)


        }


    }

    @Test
    fun `test all heroes endpoint query second page success`() {
        testApplication {

            val response = client.get("/boruto/heroes?page=2")
            assertEquals(expected = HttpStatusCode.OK, actual = response.status)

            val expected = ApiResponse(
                success = true,
                message = "ok",
                prevPage = 1,
                nextPage = 3,
                heroes = heroRepository.page2
            )

            val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText())
//
            println("EXPECTED: $expected")
            println("ACTUAL: $actual")

            assertEquals(expected, actual)


        }


    }

    @Test
    fun `access all heroes endpoints, query success`() {
        testApplication {
            val pages = 1..5
            val heroes = listOf(
                heroRepository.page1,
                heroRepository.page2,
                heroRepository.page3,
                heroRepository.page4,
                heroRepository.page5,
            )

            pages.forEach { page ->
                val response = client.get("/boruto/heroes?page=$page")
                assertEquals(expected = HttpStatusCode.OK, actual = response.status)

                val expected = ApiResponse(
                    success = true,
                    message = "ok",
                    prevPage = calculatePage(page)[PREVIOUS_PAGE_KEY],
                    nextPage = calculatePage(page)[NEXT_PAGE_KEY],
                    heroes = heroes[page - 1]
                )

                val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText())
                println("EXPECTED: $expected")
                println("ACTUAL: $actual")
                assertEquals(expected = expected, actual = actual)

            }
        }
    }

    @Test
    fun `access all heroes endpoint, query not found`() {
        testApplication {
            val response = client.get("/boruto/heroes?page=100")
            assertEquals(
                expected = HttpStatusCode.NotFound,
                actual = response.status
            )
            assertEquals(
                expected = "Page not found",
                actual = response.bodyAsText()
            )
        }
    }

    @Test
    fun `access all heroes endpoint, query bad request`() {
        testApplication {
            val response = client.get("/boruto/heroes?page=invalid")
            assertEquals(expected = HttpStatusCode.BadRequest, actual = response.status)

            val expected = ApiResponse(
                success = false,
                message = "Only Numbers Are Allowed"
            )
            val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText())

            assertEquals(
                expected = expected,
                actual = actual
            )

        }
    }

    @Test
    fun `access search heroes endpoint, query heroname, assert single hero result`() {
        testApplication {
            val response = client.get("/boruto/heroes/search?name=sas")
            assertEquals(expected = HttpStatusCode.OK, actual = response.status)

            val expected = 1
            val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).heroes.size

            assertEquals(
                expected = expected,
                actual = actual
            )

        }
    }

    @Test
    fun `access search heroes endpoint, query heroe name, assert multiple hero result`() {
        testApplication {
            val response = client.get("/boruto/heroes/search?name=sa")
            assertEquals(expected = HttpStatusCode.OK, actual = response.status)

            val expected = 3
            val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).heroes.size

            assertEquals(
                expected = expected,
                actual = actual
            )

        }
    }

    @Test
    fun `access search heroes endpoint, query an empty, assert empty list as a result`() {
        testApplication {
            val response = client.get("/boruto/heroes/search?name=")
            assertEquals(expected = HttpStatusCode.OK, actual = response.status)

            val expected = emptyList<Hero>()
            val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).heroes

            assertEquals(
                expected = expected,
                actual = actual
            )

        }
    }

    @Test
    fun `access search heroes endpoint, query non existing hero, assert empty list as a result`() {
        testApplication {
            val response = client.get("/boruto/heroes/search?name=unknown")
            assertEquals(expected = HttpStatusCode.OK, actual = response.status)

            val expected = emptyList<Hero>()
            val actual = Json.decodeFromString<ApiResponse>(response.bodyAsText()).heroes

            assertEquals(
                expected = expected,
                actual = actual
            )

        }
    }

    @Test
    fun `access non existing endpoint, assert not found`() {
        testApplication {
            val response = client.get("/unknown")
            assertEquals(expected = HttpStatusCode.NotFound, actual = response.status)

            val expected = "Page not found"
            val actual = response.bodyAsText()

            assertEquals(
                expected = expected,
                actual = actual
            )

        }
    }

}

private fun calculatePage(page: Int): Map<String, Int?> {
    var prevPage: Int? = page
    var nextPage: Int? = page

    if (page in 1..4) {
        nextPage = nextPage?.plus(1)
    }
    if (page in 2..5) {
        prevPage = prevPage?.minus(1)
    }

    if (page == 1) {
        prevPage = null
    }
    if (page == 5) {
        nextPage = null
    }


    return mapOf(NEXT_PAGE_KEY to nextPage, PREVIOUS_PAGE_KEY to prevPage)

}