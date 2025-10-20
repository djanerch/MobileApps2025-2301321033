package com.example.todolist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.AppDatabase
import com.example.todolist.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val taskDao = AppDatabase.getDatabase(application).taskDao()

    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.delete(task)
            _tasks.value = taskDao.getAllTasks()
        }
    }

    fun editTask(task: Task, newName: String) {
        if (newName.isBlank()) return

        viewModelScope.launch(Dispatchers.IO) {
            val updatedTask = task.copy(name = newName)
            taskDao.update(updatedTask)
            _tasks.value = taskDao.getAllTasks()
        }
    }
    fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            _tasks.value = taskDao.getAllTasks()
        }
    }

    fun addTask(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            taskDao.insert(Task(name = name))
            _tasks.value = taskDao.getAllTasks()
        }
    }
}
