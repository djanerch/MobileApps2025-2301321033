package com.example.todolist

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ToDoNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "task_list"
    ) {
        composable("task_list") {
            TaskListScreen(onAddClick = {
                navController.navigate("add_task")
            })
        }
        composable("add_task") {
            AddTaskScreen(onBack = { navController.popBackStack() })
        }
    }
}
