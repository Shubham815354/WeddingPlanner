package com.example.weddingplanner.my_guest_list_room_and_mvvm

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GuestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGuest(guest: GuestDatabase)

    @Update
    suspend fun updateGuest(guest: GuestDatabase)

    @Delete
    suspend fun deleteGuest(guest: GuestDatabase)

    @Query("SELECT * FROM guest_table ORDER BY id DESC")
    fun getAllGuests(): LiveData<List<GuestDatabase>>

    @Query("SELECT * FROM guest_table WHERE id = :guestId")
    suspend fun getGuestById(guestId: Int): GuestDatabase?
}