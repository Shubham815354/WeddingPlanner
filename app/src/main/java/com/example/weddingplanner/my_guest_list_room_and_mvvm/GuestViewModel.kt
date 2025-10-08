package com.example.weddingplanner.my_guest_list_room_and_mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GuestViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: GuestRepository
    val allGuests: LiveData<List<GuestDatabase>>

    init {
        val guestDao = AppDatabase.getDatabase(application).guestDao()
        repository = GuestRepository(guestDao)
        allGuests = repository.allGuests
    }

    fun insertGuest(guest: GuestDatabase) = viewModelScope.launch {
        repository.insert(guest)
    }

    fun updateGuest(guest: GuestDatabase) = viewModelScope.launch {
        repository.update(guest)
    }

    fun deleteGuest(guest: GuestDatabase) = viewModelScope.launch {
        repository.delete(guest)
    }
}