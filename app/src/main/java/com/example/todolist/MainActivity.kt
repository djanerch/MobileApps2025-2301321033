package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.todolist.ui.theme.ToDoListTheme
import com.example.todolist.viewmodel.TaskViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Създаваме ViewModel за целия екран
        val viewModel = TaskViewModel(application)

        setContent {
            ToDoListTheme {
                // Стартираме навигацията с двата екрана и подаваме ViewModel
                ToDoNavGraph(viewModel = viewModel)
            }
        }
    }
}
