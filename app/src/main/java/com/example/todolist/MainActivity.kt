package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.todolist.viewmodel.TaskViewModel
import com.example.todolist.viewmodel.TaskViewModelFactory
import com.example.todolist.ui.theme.ToDoListTheme


class MainActivity : ComponentActivity() {

    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ToDoListTheme {
                ToDoNavGraph(viewModel = viewModel)
            }
        }
    }
}