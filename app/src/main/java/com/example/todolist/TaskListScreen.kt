package com.example.todolist.ui.tasklist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.viewmodel.TaskViewModel
import com.example.todolist.data.Task

import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddClick: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()

    var taskToEdit by remember { mutableStateOf<Task?>(null) }
    var showEditDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadTasks()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Text("+")
            }
        },
        topBar = {
            TopAppBar(title = { Text("Моите задачи") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            if (tasks.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Няма задачи", style = MaterialTheme.typography.headlineSmall)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(top = 8.dp)
                ) {
                    items(tasks, key = { it.id }) { task ->
                        TaskItem(
                            task = task,
                            onEditClick = {
                                taskToEdit = task
                                showEditDialog = true
                            },
                            onDeleteClick = {
                                viewModel.deleteTask(task)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showEditDialog && taskToEdit != null) {
        EditTaskDialog(
            task = taskToEdit!!,
            onDismiss = { showEditDialog = false; taskToEdit = null },
            onConfirmEdit = { newName ->
                viewModel.editTask(taskToEdit!!, newName)
                showEditDialog = false
                taskToEdit = null
            }
        )
    }
}

@Composable
fun TaskItem(
    task: Task,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = task.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.weight(1f)
            )

            // Бутон за редактиране
            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Filled.Edit,
                    contentDescription = "Редактиране"
                )
            }

            // Бутон за изтриване
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Filled.Delete,
                    contentDescription = "Изтриване"
                )
            }
        }
    }
}

@Composable
fun EditTaskDialog(
    task: Task,
    onDismiss: () -> Unit,
    onConfirmEdit: (newName: String) -> Unit
) {
    var newName by remember { mutableStateOf(task.name) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Редактирай задача") },
        text = {
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Ново име") },
                singleLine = true
            )
        },
        confirmButton = {
            Button(
                onClick = { onConfirmEdit(newName) },
                enabled = newName.isNotBlank() && newName != task.name
            ) {
                Text("Запази")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отказ")
            }
        }
    )
}