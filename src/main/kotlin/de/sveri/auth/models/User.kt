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
        val userName: String,

        val password: String

)

fun fromSignupUser(user: SignupUser): User {
    return User(userName = user.userName, password = encryptPassword(user.password))
}

fun encryptPassword(password: String): String {
    val passwordEncoder = BCryptPasswordEncoder()
    return passwordEncoder.encode(password)
}


//data class User(val id: String)


//@Entity
//@Table(name="city")
//internal data class CityEntity(
//
//        @Id
//        @GeneratedValue(strategy= GenerationType.AUTO)
//        val id: Long? = null,
////        val name: String,
////        val description: String? = null) {
//
//        fun toDto(): User = User(
//            this.id
////            name = this.name,
////            description = this.description)
//    )
//
////    companion object {
//
////        fun fromDto(dto: CityDto) = CityEntity(
////                id = dto.id,
////                name = dto.name,
////                description = dto.description)
////
////        fun fromDto(dto: CreateCityDto) = CityEntity(
////                name = dto.name,
////                description = dto.description)
//
////        fun fromDto(dto: UpdateCityDto, defaultCity: CityEntity) = CityEntity(
////                id = defaultCity.id!!,
////                name = dto.name ?: defaultCity.name,
////                description = dto.description ?: defaultCity.description)
////    }
//}
