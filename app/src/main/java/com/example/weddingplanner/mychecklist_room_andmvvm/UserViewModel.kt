package com.example.weddingplanner.mychecklist_room_andmvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weddingplanner.my_activity_2data_models.Checklist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class UserViewModel(val repository: UserRepository) : ViewModel() {

    private val _exist_data = MutableLiveData<List<UserDatabase>>()
    val existData: LiveData<List<UserDatabase>> = _exist_data

    fun getCheckData() {
        try {
            viewModelScope.launch {
                val response = withContext(Dispatchers.IO) {
                    repository.get_all()
                }
                _exist_data.postValue(response)
            }
        } catch (e: Exception) {
            Log.e("Error From Get", e.localizedMessage ?: "Unknown error")
        }
    }

    fun check_exist(user: UserDatabase) {
        try {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    repository.check_existing(user)
                }
                getCheckData()
            }
        } catch (e: Exception) {
            Log.e("Error From Check Exist", e.localizedMessage ?: "Unknown error")
        }
    }

    // 🔹 NEW: delete a task
    fun deleteCheck(user: UserDatabase) {
        viewModelScope.launch {
            if (!user.isDefault) {
                repository.delete_task(user)  // now will delete by taskName
                getCheckData() // refresh LiveData
            }
        }
    }


    fun insertDefaultTasks() {
        viewModelScope.launch {
            repository.insertDefaultTasksIfNeeded()
            getCheckData() // Refresh LiveData so RecyclerView shows data
        }
    }
    fun updateCheckStatus(taskName: String, checked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val task = repository.getTaskByName(taskName)
            if (task != null) {
                repository.updateTaskChecked(task.copy(checked = checked))
            }
        }
    }




}
