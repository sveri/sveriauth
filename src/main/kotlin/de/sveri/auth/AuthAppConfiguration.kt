package de.sveri.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebSecurity
class AuthAppConfiguration {

    @Bean
    fun appConfigurer(): WebSecurityConfigurerAdapter {
        return object : WebSecurityConfigurerAdapter() {
            override fun configure(web: WebSecurity) {
                web.ignoring().antMatchers("/api/user/signup/**")
            }
        }

    }

    @Profile("!prod")
    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
            }
        }
    }
}