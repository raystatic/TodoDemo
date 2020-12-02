package com.example.todo.repository

import com.example.todo.db.Todo
import com.example.todo.db.TodoDao
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoDao: TodoDao
){

    suspend fun upsertTodo(todo: Todo) = todoDao.upsertTodo(todo)

    fun getAllTodo() = todoDao.getAllTodo()

    fun getCompleteTodo() = todoDao.getCompletedTodo()

    fun getIncompleteTodo() = todoDao.getInCompletedTodo()

    suspend fun deleteTodoById(id:String) = todoDao.deleteTodoById(id)

    suspend fun deleteAllTodo() = todoDao.deleteAllTodo()

}