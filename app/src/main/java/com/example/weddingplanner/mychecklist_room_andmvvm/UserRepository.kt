package com.example.weddingplanner.mychecklist_room_andmvvm

class UserRepository(private val dao: UserDao) {

    suspend fun check_existing(user: UserDatabase) {
        val exist = dao.existing_check(user.taskName)
        if (exist != null) {
            dao._deleteCheck(exist)
        } else {
            dao.inserft_checklist(user)
        }
    }

    suspend fun get_all(): List<UserDatabase> {
        return dao.get_allcheck()
    }

    // 🔹 NEW: delete a task explicitly
    suspend fun delete_task(user: UserDatabase) {
        dao.deleteByTaskName(user.taskName)  // delete by taskName instead of object
    }

    suspend fun getTaskByName(taskName: String): UserDatabase? {
        return dao.existing_check(taskName) // Your DAO already has this
    }

    suspend fun updateTaskChecked(user: UserDatabase) {
        dao.updateChecked(user.id, user.checked)
    }
    suspend fun deleteCheck(user: UserDatabase) {
        // delete by taskName
        dao.deleteByTaskName(user.taskName)
    }


    suspend fun insertDefaultTasksIfNeeded() {
        val defaultTasks = listOf(
            "Venue Booking",
            "Photography",
            "Catering",
            "Mehendi",
            "Sangeet",
            "Honeymoon Booking"
        )
        defaultTasks.forEach { task ->
            val exist = dao.existing_check(task)
            if (exist == null) {
                dao.inserft_checklist(UserDatabase(taskName = task, checked = false, isDefault = true))
            }
        }
    }

}
