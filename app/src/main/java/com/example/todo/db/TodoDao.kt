package com.example.todo.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertTodo(todo: Todo)

    @Query("SELECT * FROM todo ORDER BY creationTime DESC")
    fun getAllTodo():LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE isCompleted ORDER BY creationTime DESC ")
    fun getCompletedTodo():LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE not isCompleted ORDER BY creationTime DESC")
    fun getInCompletedTodo():LiveData<List<Todo>>

    @Query("DELETE FROM todo WHERE id=:id")
    suspend fun deleteTodoById(id:String)

    @Query("DELETE FROM todo")
    suspend fun deleteAllTodo()

}