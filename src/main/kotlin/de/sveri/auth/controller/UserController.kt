package de.sveri.auth.controller

import de.sveri.auth.models.User
import de.sveri.auth.models.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.ws.rs.Produces


@RestController
@RequestMapping("/user")
@Produces("application/json")
class UserController constructor(val userRepository: UserRepository) {

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
            val updatedUser = oldUser.copy(userName = user.userName, email = user.email)
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