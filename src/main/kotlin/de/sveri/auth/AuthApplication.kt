package de.sveri.auth

import de.sveri.auth.models.DefaultUserDao
import de.sveri.auth.models.UserDao
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class AuthApplication {

    @Bean
    fun userDao(): UserDao = DefaultUserDao()
}

fun main(args: Array<String>) {
    runApplication<AuthApplication>(*args)
}
