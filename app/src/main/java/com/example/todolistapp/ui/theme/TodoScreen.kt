package com.example.todolistapp.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistapp.data.TodoItem
import com.example.todolistapp.viewmodel.TodoViewModel

@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    // Coleta o estado da lista direto do banco de dados
    val todoList by viewModel.todoList.collectAsState(initial = emptyList())
    var textInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Minhas Tarefas",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Área de Input (Campo de texto + Botão)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = textInput,
                onValueChange = { textInput = it },
                label = { Text("Nova tarefa") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (textInput.isNotBlank()) {
                    viewModel.addTodo(textInput)
                    textInput = "" // Limpa o campo
                }
            }) {
                Text("Add")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de Tarefas (LazyColumn é a "RecyclerView" do Compose)
        LazyColumn {
            items(todoList) { item ->
                TodoItemRow(
                    item = item,
                    onToggle = { viewModel.toggleTodo(item) },
                    onDelete = { viewModel.deleteTodo(item) }
                )
            }
        }
    }
}

// Componente visual de cada linha da lista
@Composable
fun TodoItemRow(
    item: TodoItem,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Checkbox(
                checked = item.isDone,
                onCheckedChange = { onToggle() }
            )
            Text(
                text = item.title,
                modifier = Modifier.weight(1f),
                textDecoration = if (item.isDone) TextDecoration.LineThrough else null,
                color = if (item.isDone) Color.Gray else Color.Black
            )
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Deletar",
                    tint = Color.Red
                )
            }
        }
    }
}