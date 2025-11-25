package com.example.todolistapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolistapp.data.AppDatabase
import com.example.todolistapp.data.TodoItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TodoViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getDatabase(application)
    private val dao = db.todoDao()

    val todoList: Flow<List<TodoItem>> = dao.getAllTodos()

    fun addTodo(title: String) {
        viewModelScope.launch {
            dao.insert(TodoItem(title = title))
        }
    }

    fun deleteTodo(item: TodoItem) {
        viewModelScope.launch {
            dao.delete(item)
        }
    }

    fun toggleTodo(item: TodoItem) {
        viewModelScope.launch {
            dao.update(item.copy(isDone = !item.isDone))
        }
    }

    // --- ESSA É A FUNÇÃO QUE ESTAVA FALTANDO ---
    fun updateTodoTitle(item: TodoItem, newTitle: String) {
        viewModelScope.launch {
            dao.update(item.copy(title = newTitle))
        }
    }
}