package com.example.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.AppDatabase
import com.example.todolistapp.data.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    // Inicializa o banco de dados
    private val db = AppDatabase.getDatabase(application)
    private val dao = db.todoDao()

    // Essa variável "todoList" é o que a tela vai ficar observando
    // Ela vem direto do Banco de Dados (Flow)
    val todoList: Flow<List<TodoItem>> = dao.getAllTodos()

    // Função para adicionar (chamada pelo botão Add)
    fun addTodo(title: String) {
        viewModelScope.launch {
            dao.insert(TodoItem(title = title))
        }
    }

    // Função para deletar (chamada pelo ícone de lixeira)
    fun deleteTodo(item: TodoItem) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }

    // Função para marcar como feito (chamada pelo Checkbox)
    fun toggleTodo(item: TodoItem) {
        viewModelScope.launch {
            dao.update(item.copy(isDone = !item.isDone))
        }
    }
}