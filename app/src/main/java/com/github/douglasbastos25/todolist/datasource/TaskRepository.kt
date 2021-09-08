package com.github.douglasbastos25.todolist.datasource

import com.github.douglasbastos25.todolist.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class TaskRepository(private val dao: TaskDao) {
    fun insert(task: Task) = runBlocking {
        launch(Dispatchers.IO) {
            dao.insert(task)
        }
    }

    fun getAll() = dao.getAll()

    fun getTaskById(id: Int) = dao.getTaskById(id)

    fun deleteById(id: Int) = runBlocking {
        launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    fun update(task: Task) = runBlocking {
        launch {
            dao.update(task)
        }
    }
}