package com.example.todolist.ui.tasklist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.viewmodel.TaskViewModel // 👈 Still needed for type

@Composable
fun TaskListScreen(
    // ⬇️ FIX: Добави ViewModel като параметър ⬇️
    viewModel: TaskViewModel,
    onAddClick: () -> Unit
) {
    // No need for LocalContext, Application, or Factory—the ViewModel is passed in.

    val tasks by viewModel.tasks.collectAsState()

    // Assuming loadTasks() is still necessary for initial fetch or logic
    LaunchedEffect(Unit) { viewModel.loadTasks() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) { Text("+") }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).padding(16.dp)) {
            if (tasks.isEmpty()) {
                Text("Няма задачи")
            } else {
                tasks.forEach { task ->
                    Text(task.name)
                }
            }
        }
    }
}