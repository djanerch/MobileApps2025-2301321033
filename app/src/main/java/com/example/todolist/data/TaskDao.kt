package com.example.todolist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
// Removed Flow import

@Dao
interface TaskDao {
    @Insert
    suspend fun insert(task: Task)

    // ⬇️ FIX: Must be 'suspend' and return 'List<Task>' to match your ViewModel ⬇️
    @Query("SELECT * FROM tasks ORDER BY id ASC")
    suspend fun getAllTasks(): List<Task>
}