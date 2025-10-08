package com.example.weddingplanner.my_guest_list_room_and_mvvm

import androidx.lifecycle.LiveData

class GuestRepository(private val guestDao: GuestDao) {

    val allGuests: LiveData<List<GuestDatabase>> = guestDao.getAllGuests()

    suspend fun insert(guest: GuestDatabase) {
        guestDao.insertGuest(guest)
    }

    suspend fun update(guest: GuestDatabase) {
        guestDao.updateGuest(guest)
    }

    suspend fun delete(guest: GuestDatabase) {
        guestDao.deleteGuest(guest)
    }

    suspend fun getGuestById(id: Int): GuestDatabase? {
        return guestDao.getGuestById(id)
    }
}