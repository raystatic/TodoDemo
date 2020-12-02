package com.example.todo.ui.viewmodels

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.db.Todo
import com.example.todo.repository.TodoRepository
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
    private val repository: TodoRepository
): ViewModel(){

    val todos = repository.getAllTodo()

    val completeTodo = repository.getCompleteTodo()

    val incompleteTodo = repository.getIncompleteTodo()

    fun upsertTodo(todo: Todo) = viewModelScope.launch {
        repository.upsertTodo(todo)
    }

    fun deleteTodoById(id:String) = viewModelScope.launch {
        repository.deleteTodoById(id)
    }

    fun deleteAllTodo() = viewModelScope.launch {
        repository.deleteAllTodo()
    }

}