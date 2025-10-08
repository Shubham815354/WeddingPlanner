package com.example.weddingplanner.myauthMvvm

import android.content.Context
import android.content.SharedPreferences
import com.example.weddingplanner.my_auth_room_db.User_dao
import com.example.weddingplanner.my_auth_room_db.Users
import com.example.weddingplanner.my_login_activity_and_fragments.User
import com.example.weddingplanner.mysharedpref.SharedPrefHelper

class UserRepository(private val dao: User_dao,private val context :Context) {
    private val sharedPrefHelper = SharedPrefHelper(context)

    suspend fun registerUser(user: Users): Boolean {
        val existingUser = dao.getUserByEmail(user.email)
        return if (existingUser == null) {
            dao.insertUser(user)
            true
        } else {
            false // email already exists
        }
    }

    suspend fun loginUser(email: String, password: String): Boolean {
        val user = dao.getUserByEmail(email)
        return if (user?.password == password) {
            // Save first name and last name in SharedPreferences
            sharedPrefHelper.saveUserDetails(user.firstName, user.lastName)
            true
        } else {
            false
        }
    }

}