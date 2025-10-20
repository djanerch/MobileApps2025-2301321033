package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
            var isDarkThemeEnabled by remember { mutableStateOf(false) }

            ToDoListTheme(darkTheme = isDarkThemeEnabled) {
                ToDoNavGraph(
                    viewModel = viewModel,
                    isDarkThemeEnabled = isDarkThemeEnabled,
                    onToggleTheme = { isDarkThemeEnabled = it }
                )
            }
        }
    }
}