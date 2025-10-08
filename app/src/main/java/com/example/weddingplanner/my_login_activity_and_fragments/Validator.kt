package com.example.weddingplanner.my_login_activity_and_fragments

import android.util.Patterns

object Validator {
    fun isValidName(name: String): Boolean {
        return name.length >= 3
    }

    fun isValidEmailStrict(email: String): Boolean {
        val emailPattern = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.(com|org|net)\$"
        return Regex(emailPattern).matches(email)
    }


    fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=!]).{8,}$"
        return Regex(passwordPattern).matches(password)
    }
}