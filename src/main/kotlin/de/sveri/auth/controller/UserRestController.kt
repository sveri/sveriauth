package de.sveri.auth.controller

import de.sveri.auth.helper.JwtHelper
import de.sveri.auth.models.User
import de.sveri.auth.models.fromSignupUser
import de.sveri.auth.models.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.ws.rs.Produces
import org.springframework.beans.factory.annotation.Autowired
import java.util.*
import javax.validation.constraints.AssertTrue
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull


data class LoginUser(val userName: String, val password: String)

data class SignupUser(
        @NotNull @Email val userName: String,

        @NotNull @Min(6) val password: String,

        @NotNull @Min(6) val password_confirm: String,

        @NotNull @AssertTrue val checkbox: Boolean)

data class ReturnUser(val userName: String, val token: String)


@RestController
@RequestMapping("/api/user")
@Produces("application/json")
class UserRestController constructor(val userRepository: UserRepository) {

    @Autowired
    private val jwtHelper: JwtHelper? = null

    @PostMapping("/login")
    fun login(@RequestBody user: LoginUser): ReturnUser {

        val token = Jwts.builder().setSubject(user.userName)
                .setIssuedAt(Date())
                .signWith(SignatureAlgorithm.HS256, jwtHelper?.secretKey).compact()

        return ReturnUser(user.userName, token)
    }

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody user: SignupUser): ReturnUser {

        val fromSignupUser = fromSignupUser(user)
//        userRepository.save(fromSignupUser)

        val token = Jwts.builder().setSubject(user.userName)
                .setIssuedAt(Date())
                .signWith(SignatureAlgorithm.HS256, jwtHelper?.secretKey).compact()

        return ReturnUser(user.userName, token)
    }

    @RequestMapping(path = arrayOf("/{id}"), method = arrayOf(RequestMethod.GET))
    fun getUser(@PathVariable("id") id: Long): User {

        return userRepository.getOne(id)
    }

    @PostMapping("/")
    fun createUser(@Valid @RequestBody user: User): User = userRepository.save(user)

    @PutMapping("/{id}")
    fun updateUser(@PathVariable("id") id: Long,
                   @Valid @RequestBody user: User) {
        userRepository.findById(id).map { oldUser ->
            val updatedUser = oldUser.copy(userName = user.userName)
            ResponseEntity.ok().body(userRepository.save(updatedUser))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable("id") id: Long): ResponseEntity<Void> {
        return userRepository.findById(id).map { user ->
            userRepository.delete(user)
            ResponseEntity<Void>(HttpStatus.OK)

        }.orElse(ResponseEntity.notFound().build())
    }
}
