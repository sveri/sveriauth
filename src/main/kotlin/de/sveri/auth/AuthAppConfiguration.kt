package de.sveri.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
class AuthAppConfiguration {

    @Bean
    fun appConfigurer(): WebSecurityConfigurerAdapter {
        return object : WebSecurityConfigurerAdapter() {
            override fun configure(web: WebSecurity) {
                web.ignoring().antMatchers("/api/user/signup/**")
            }
        }

    }
}