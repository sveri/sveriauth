package de.sveri.auth.models

interface UserDao {

    fun getUser(): User;
}