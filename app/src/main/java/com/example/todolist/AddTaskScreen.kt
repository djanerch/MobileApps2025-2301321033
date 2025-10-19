package com.example.todolist

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.viewmodel.TaskViewModel // 👈 NEW IMPORT

// Add the OptIn annotation here
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    // ⬇️ ADD VIEW MODEL ⬇️
    viewModel: TaskViewModel,
    onSaveComplete: () -> Unit // Call this when saving is done
) {
    var text by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Добави задача") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Задача") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                // ⬇️ FIX: Add save logic and disable button if text is empty ⬇️
                onClick = {
                    if (text.isNotBlank()) {
                        viewModel.addTask(text) // 1. Save the task
                        onSaveComplete()        // 2. Navigate back
                    }
                },
                enabled = text.isNotBlank(), // Button is enabled only if text is not blank
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Запази")
            }
        }
    }
}