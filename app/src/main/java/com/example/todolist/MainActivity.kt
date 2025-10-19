package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
// ⬇️ NEW IMPORTS ⬇️
import androidx.activity.viewModels
import com.example.todolist.viewmodel.TaskViewModel
import com.example.todolist.viewmodel.TaskViewModelFactory
// ⬆️ NEW IMPORTS ⬆️
import com.example.todolist.ui.theme.ToDoListTheme


class MainActivity : ComponentActivity() {

    // ⬇️ FIX: Use the 'by viewModels' delegate with the custom factory ⬇️
    // This creates a lifecycle-aware ViewModel scoped to the Activity.
    private val viewModel: TaskViewModel by viewModels {
        TaskViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // The viewModel is now correctly created by the delegate above
        // and is accessible here.

        setContent {
            ToDoListTheme {
                // Стартираме навигацията с двата екрана и подаваме ViewModel
                // The correct lifecycle-aware instance is passed to the NavGraph
                ToDoNavGraph(viewModel = viewModel)
            }
        }
    }
}