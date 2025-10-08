package com.example.weddingplanner.my_auth_room_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weddingplanner.my_login_activity_and_fragments.User

@Dao
interface User_dao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: Users)

    // Check if email already exists (to prevent duplicate signup)
    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): Users?

    // Login check → match email and password
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun loginUser(email: String, password: String): Users?

    // (Optional) Get all users - for debugging/admin
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<Users>

    // (Optional) Delete a user
    @Query("DELETE FROM users WHERE email = :email")
    suspend fun deleteUser(email: String): Int
}