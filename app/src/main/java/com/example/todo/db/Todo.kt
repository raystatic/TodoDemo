package com.example.todo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id:String,

    @ColumnInfo(name = "task")
    var task:String,

    @ColumnInfo(name = "isCompleted")
    var isCompleted:Boolean,

    @ColumnInfo(name = "creationTime")
    var creationTime:Long
)