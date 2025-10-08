package com.example.weddingplanner.mychecklist_room_andmvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weddingplanner.myauthMvvm.UserViewModel

class UserViewModelFactory(val repository: UserRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create (modelclass:Class<T>):T{
        if(modelclass.isAssignableFrom(com.example.weddingplanner.mychecklist_room_andmvvm.UserViewModel::class.java)){
            return UserViewModel(repository) as T
        }
        throw IllegalArgumentException("Uknown View Model Class")
    }
}