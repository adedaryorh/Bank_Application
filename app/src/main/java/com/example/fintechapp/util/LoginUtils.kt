package com.example.fintechapp.util

import android.util.Patterns

// Extensions and Utilities
object LoginUtils {
    fun validateEmail(email: String): Boolean {
        return email.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(password: String): Boolean {
        return password.isNotBlank() && password.length >= 6
    }
}