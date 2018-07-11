package de.sveri.auth.controller

import de.sveri.auth.helper.JwtHelper
import de.sveri.auth.models.User
import de.sveri.auth.models.repository.UserRepository
import de.sveri.auth.validator.EqualFields
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.ws.rs.Produces
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.Errors
import java.util.*
import javax.validation.constraints.*


data class LoginUser(val userName: String, val password: String)

@EqualFields(baseField = "password", matchField = "password_confirm")
//@EqualFields
data class SignupUser(
        @field:Size(min = 3) val userName: String,

        @field:Size(min = 6) val password: String,

        @field:Size(min = 6)  val password_confirm: String,

        @field:AssertTrue val tosAccepted: Boolean)

data class ReturnUser(val userName: String, val token: String)


@RestController
@RequestMapping("/api/user")
@Produces("application/json")
class UserRestController constructor(val userRepository: UserRepository) {

    @Autowired
    private val jwtHelper: JwtHelper? = null

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody signupUser: SignupUser): ReturnUser {

        val user = User.fromSignupUser(signupUser)
        userRepository.save(user)

        val token = Jwts.builder().setSubject(signupUser.userName)
                .setIssuedAt(Date())
                .signWith(SignatureAlgorithm.HS256, jwtHelper?.secretKey).compact()

        return ReturnUser(signupUser.userName, token)
    }

    @PostMapping("/login")
    fun login(@RequestBody user: LoginUser): ReturnUser {

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
