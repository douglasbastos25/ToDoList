package com.github.douglasbastos25.todolist

import android.app.Application
import com.github.douglasbastos25.todolist.datasource.AppDatabase
import com.github.douglasbastos25.todolist.datasource.TaskRepository

class App: Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }
}