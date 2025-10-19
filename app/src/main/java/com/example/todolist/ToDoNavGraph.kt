// ToDoNavGraph.kt (Файлът, който трябва да бъде коригиран)

package com.example.todolist

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todolist.viewmodel.TaskViewModel

@Composable
fun ToDoNavGraph(viewModel: TaskViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "taskList") {

        composable("taskList") {
            TaskListScreen(
                viewModel = viewModel, // ⬅️ Сега вече е коректно
                onAddClick = { navController.navigate("addTask") }
            )
        }

        composable("addTask") {
            AddTaskScreen(
                // 2. Подаваме ViewModel
                viewModel = viewModel,
                // 3. FIX: Използваме onSaveComplete, за да съвпадне със сигнатурата на AddTaskScreen
                onSaveComplete = { navController.popBackStack() }
            )
        }
    }
}