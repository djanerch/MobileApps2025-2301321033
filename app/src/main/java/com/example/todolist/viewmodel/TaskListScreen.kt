@Composable
fun TaskListScreen(
    viewModel: TaskViewModel,
    onAddClick: () -> Unit
) {
    val tasks by viewModel.tasks.collectAsState()

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
