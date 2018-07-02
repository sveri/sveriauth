package de.sveri.auth.models

import javax.persistence.*
import javax.validation.constraints.NotBlank

@Entity
data class User(
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long = 0,

        @get: NotBlank
        val userName: String = "",

        val email: String = ""
)



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
