package com.example.todolist.ui.tasklist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.viewmodel.TaskViewModel
import com.example.todolist.data.Task

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddClick: () -> Unit,
    isDarkThemeEnabled: Boolean, // Приемаме текущото състояние
    onToggleTheme: (Boolean) -> Unit
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
            TopAppBar(
                title = { Text("Моите задачи") },
                actions = {
                    // SWITCH КОМПОНЕНТ
                    Switch(
                        checked = isDarkThemeEnabled, // Използваме подаденото състояние
                        onCheckedChange = { isChecked ->
                            onToggleTheme(isChecked)
                        },
                        thumbContent = {
                            Icon(
                                Icons.Filled.Brightness4,
                                contentDescription = "Превключи тема",
                                modifier = Modifier.size(SwitchDefaults.IconSize)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            )
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

            IconButton(onClick = onEditClick) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Редактиране"
                )
            }

            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Filled.Delete,
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