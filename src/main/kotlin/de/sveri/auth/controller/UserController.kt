package de.sveri.auth.controller

import de.sveri.auth.models.User
import de.sveri.auth.models.UserDao
import de.sveri.auth.models.repository.UserRepository
import javax.ws.rs.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
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
}