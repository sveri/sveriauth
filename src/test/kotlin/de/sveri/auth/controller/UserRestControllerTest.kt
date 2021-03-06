package de.sveri.auth.controller

import de.sveri.auth.AuthApplication
import de.sveri.auth.models.repository.UserRepository
import io.restassured.RestAssured
import io.restassured.RestAssured.*
import io.restassured.config.RestAssuredConfig
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.postForEntity
import org.springframework.test.context.junit4.SpringRunner
import io.restassured.mapper.ObjectMapperType
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.junit.Before
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.web.reactive.function.server.RequestPredicates.contentType
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.web.WebAppConfiguration


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserRestControllerTest : RestAssuredConfig() {

    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        RestAssured.port = port
    }

    @Test
    fun validationUserShouldReturnToken() {
        val signupUser = SignupUser("username", "password", "password", true)

        given().contentType("application/json").body(signupUser, ObjectMapperType.JACKSON_2).`when`()
                .post("/api/user/signup").then().statusCode(`is`(HttpStatus.OK.value()))
                .body("token", notNullValue()).body("userName", `is`("username"))

        println("-------------")
        println(userRepository.findAll().size)
        println("-------------")
    }

    @Test
    fun validationErroShouldReturnBadRequest() {
        expectBadRequest(SignupUser("u", "password", "password", true))
        expectBadRequest(SignupUser("username", "passw", "password", true))
        expectBadRequest(SignupUser("username", "password", "pasd", true))
        expectBadRequest(SignupUser("username", "password", "pasasldkfjd", false))
        expectBadRequest(SignupUser("username", "password", "password2", true))
    }


    fun expectBadRequest(signupUser: SignupUser) {
        given().contentType("application/json").body(signupUser, ObjectMapperType.JACKSON_2).`when`()
                .post("/api/user/signup").then().statusCode(`is`(HttpStatus.BAD_REQUEST.value()))
                .body("type", `is`(RestErrorTypes.VALIDATION.name))
    }

    @Test
    fun signupUserShouldStoreInDb() {
        val userName = "username"
        val signupUser = SignupUser(userName, "password", "password", true)

        given().contentType("application/json").body(signupUser, ObjectMapperType.JACKSON_2).`when`()
                .post("/api/user/signup").then().statusCode(`is`(HttpStatus.OK.value()))
                .body("token", notNullValue()).body("userName", `is`(userName))


    }

}