package com.example.todolist

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.viewmodel.TaskViewModel
import com.example.todolist.ui.tasklist.TaskListScreen

@Composable
fun ToDoNavGraph(
    viewModel: TaskViewModel,
    isDarkThemeEnabled: Boolean,
    onToggleTheme: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "taskList") {

        composable("taskList") {
            TaskListScreen(
                viewModel = viewModel,
                onAddClick = { navController.navigate("addTask") },
                isDarkThemeEnabled = isDarkThemeEnabled,
                onToggleTheme = onToggleTheme
            )
        }

        composable("addTask") {
            AddTaskScreen(
                viewModel = viewModel,
                onSaveComplete = { navController.popBackStack() }
            )
        }
    }
}