package com.example.weddingplanner.mychecklist_room_andmvvm

import androidx.room.Database
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "check_db")
data class UserDatabase(@PrimaryKey (autoGenerate = true) val id:Int=0,
    val checked:Boolean, val taskName:String, val isDefault :Boolean)