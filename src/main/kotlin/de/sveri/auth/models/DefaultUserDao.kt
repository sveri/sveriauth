package de.sveri.auth.models

class DefaultUserDao : UserDao{
    override fun getUser(): User {
        return User(1111)
    }

}

