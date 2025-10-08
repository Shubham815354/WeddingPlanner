package com.example.weddingplanner.my_guest_list_room_and_mvvm

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guest_table")
data class GuestDatabase(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val age: Int,
    val gender: String,
    val rsvpStatus: String = "Pending"
)