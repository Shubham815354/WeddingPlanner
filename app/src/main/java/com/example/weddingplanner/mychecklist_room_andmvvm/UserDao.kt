package com.example.weddingplanner.mychecklist_room_andmvvm

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserft_checklist(user: UserDatabase)

    @Query("SELECT * FROM check_db WHERE taskName =:taskName LIMIT 1")
   suspend fun existing_check(taskName:String): UserDatabase?

    @Query("SELECT * FROM check_db")
    suspend fun get_allcheck(): List<UserDatabase>

    @Delete()
    suspend fun _deleteCheck(user: UserDatabase)

    @Query("UPDATE check_db SET checked = :checked WHERE id = :id")
    suspend fun updateChecked(id: Int, checked: Boolean)

    @Query("DELETE FROM check_db WHERE taskName = :taskName")
    suspend fun deleteByTaskName(taskName: String)
}