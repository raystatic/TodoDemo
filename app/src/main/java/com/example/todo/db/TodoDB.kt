package com.example.todo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [Todo::class],
    version = 1
)
abstract class TodoDB: RoomDatabase(){

    abstract fun getTodoDao(): TodoDao

}