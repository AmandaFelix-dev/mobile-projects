package com.example.todolistapp.ui.theme

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolistapp.data.TodoItem
import com.example.todolistapp.viewmodel.TodoViewModel

// --- CORES (Mesmas do tema anterior) ---
private val DarkBackground = Color(0xFF111827)
private val CardBackground = Color(0xFF1F2937)
private val PrimaryAccent = Color(0xFF6366F1)
private val TextWhite = Color(0xFFF9FAFB)
private val TextGray = Color(0xFF9CA3AF)
private val GreenSuccess = Color(0xFF10B981)
private val RedDelete = Color(0xFFEF4444)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(viewModel: TodoViewModel = viewModel()) {
    val todoList by viewModel.todoList.collectAsState(initial = emptyList())
    var textInput by remember { mutableStateOf("") }

    // --- ESTADOS PARA O DIALOG DE EDIÇÃO ---
    var showDialog by remember { mutableStateOf(false) }
    var itemToEdit by remember { mutableStateOf<TodoItem?>(null) }
    var editTextInput by remember { mutableStateOf("") }

    // Função auxiliar para abrir o dialog
    fun openEditDialog(item: TodoItem) {
        itemToEdit = item
        editTextInput = item.title
        showDialog = true
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Dev Tasks", fontWeight = FontWeight.Bold, color = TextWhite) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DarkBackground)
            )
        },
        containerColor = DarkBackground
    ) { paddingValues ->

        // --- CONTEÚDO DA TELA ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Card de Adicionar (Igual ao anterior)
            Card(
                colors = CardDefaults.cardColors(containerColor = CardBackground),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(8.dp)
                ) {
                    OutlinedTextField(
                        value = textInput,
                        onValueChange = { textInput = it },
                        placeholder = { Text("Nova tarefa...", color = TextGray) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            cursorColor = PrimaryAccent,
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    FloatingActionButton(
                        onClick = {
                            if (textInput.isNotBlank()) {
                                viewModel.addTodo(textInput)
                                textInput = ""
                            }
                        },
                        containerColor = PrimaryAccent,
                        contentColor = Color.White,
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Adicionar")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(todoList) { item ->
                    TodoItemRow(
                        item = item,
                        onToggle = { viewModel.toggleTodo(item) },
                        onDelete = { viewModel.deleteTodo(item) },
                        onEdit = { openEditDialog(item) } // Passamos a ação de editar
                    )
                }
            }
        }

        // --- O DIALOG DE EDIÇÃO (Aparece sobre a tela) ---
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                containerColor = CardBackground,
                title = { Text("Editar Tarefa", color = TextWhite) },
                text = {
                    OutlinedTextField(
                        value = editTextInput,
                        onValueChange = { editTextInput = it },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            cursorColor = PrimaryAccent,
                            focusedBorderColor = PrimaryAccent,
                            unfocusedBorderColor = TextGray
                        )
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            itemToEdit?.let { item ->
                                if (editTextInput.isNotBlank()) {
                                    viewModel.updateTodoTitle(item, editTextInput)
                                }
                            }
                            showDialog = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryAccent)
                    ) {
                        Text("Salvar", color = TextWhite)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar", color = TextGray)
                    }
                }
            )
        }
    }
}

@Composable
fun TodoItemRow(
    item: TodoItem,
    onToggle: () -> Unit,
    onDelete: () -> Unit,
    onEdit: () -> Unit // Novo parâmetro
) {
    val cardColor by animateColorAsState(
        targetValue = if (item.isDone) Color(0xFF064E3B) else CardBackground, label = "color"
    )
    val scale by animateFloatAsState(
        targetValue = if (item.isDone) 0.98f else 1f, label = "scale"
    )

    Card(
        modifier = Modifier.fillMaxWidth().scale(scale),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Checkbox(
                checked = item.isDone,
                onCheckedChange = { onToggle() },
                colors = CheckboxDefaults.colors(checkedColor = GreenSuccess, uncheckedColor = TextGray)
            )

            Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                Text(
                    text = item.title,
                    color = if (item.isDone) TextGray else TextWhite,
                    fontSize = 16.sp,
                    textDecoration = if (item.isDone) TextDecoration.LineThrough else null
                )
            }

            // BOTÃO DE EDITAR (Lápis)
            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Editar",
                    tint = PrimaryAccent
                )
            }

            // BOTÃO DE DELETAR
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Deletar",
                    tint = RedDelete.copy(alpha = 0.8f)
                )
            }
        }
    }
}