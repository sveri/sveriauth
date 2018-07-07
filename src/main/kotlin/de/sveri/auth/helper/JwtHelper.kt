package de.sveri.auth.helper

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component


@Component
class JwtHelper {

    @Value("\${app.jwt.secretKey}")
    val secretKey: String? = null

    fun getSubject(token: String): String {
        return io.jsonwebtoken.Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject()
    }

    fun getRole(token: String): String {
        return io.jsonwebtoken.Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role",
                String::class.java)
    }

//    companion object {
//
//        const val BEARER = "Bearer "
//
//        const val AUTHORIZATION = "Authorization"
//    }
}