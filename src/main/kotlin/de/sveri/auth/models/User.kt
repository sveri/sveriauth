package de.sveri.auth.models

import de.sveri.auth.controller.SignupUser
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank


@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,

        @get: NotBlank
        val userName: String = "",

        val password: String = ""
) {
    companion object {
        fun fromSignupUser(user: SignupUser): User {
            return User(userName = user.userName, password = encryptPassword(user.password))
        }

        fun encryptPassword(password: String): String {
            val passwordEncoder = BCryptPasswordEncoder()
            return passwordEncoder.encode(password)
        }
    }
}
