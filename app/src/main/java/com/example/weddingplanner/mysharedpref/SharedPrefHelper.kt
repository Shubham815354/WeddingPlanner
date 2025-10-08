package com.example.weddingplanner.mysharedpref

import android.content.Context

class SharedPrefHelper(context:Context) {
    private val sharedPref = context.getSharedPreferences("user_pref", Context.MODE_PRIVATE)

    // Save first name and last name
    fun saveUserDetails(firstName: String, lastName: String) {
        with(sharedPref.edit()) {
            putString("first_name", firstName)
            putString("last_name", lastName)
            apply() // asynchronously save
        }
    }

    // Retrieve first name
    fun getFirstName(): String {
        return sharedPref.getString("first_name", "") ?: ""
    }

    // Retrieve last name
    fun getLastName(): String {
        return sharedPref.getString("last_name", "") ?: ""
    }

    // Retrieve both as Pair
    fun getUserDetails(): Pair<String, String> {
        val firstName = getFirstName()
        val lastName = getLastName()
        return Pair(firstName, lastName)
    }

    // Clear data if needed
    fun clearUserDetails() {
        with(sharedPref.edit()) {
            clear()
            apply()
        }
    }
}