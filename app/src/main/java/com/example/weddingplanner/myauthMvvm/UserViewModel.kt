package com.example.weddingplanner.myauthMvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weddingplanner.my_auth_room_db.Users
import com.example.weddingplanner.my_login_activity_and_fragments.User
import kotlinx.coroutines.launch

class UserViewModel (val repository: UserRepository): ViewModel(){
    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean> = _registerStatus

    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> = _loginStatus

    fun registerUser(user: Users) {
        viewModelScope.launch {
            val success = repository.registerUser(user)
            _registerStatus.postValue(success)
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val success = repository.loginUser(email, password)
            _loginStatus.postValue(success)
        }
    }
}