package com.github.douglasbastos25.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var title: String,
    var time: String,
    var date: String
)
