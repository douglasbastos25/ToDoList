package com.github.douglasbastos25.todolist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.github.douglasbastos25.todolist.datasource.TaskRepository
import com.github.douglasbastos25.todolist.model.Task

class MainViewModel (private val taskRepository: TaskRepository): ViewModel() {

    fun insert(task: Task){
        taskRepository.insert(task)
    }

    fun getAll() : LiveData<List<Task>>{
        return taskRepository.getAll()
    }

    fun getTaskById(id: Int): LiveData<Task>{
        return taskRepository.getTaskById(id)
    }

    fun deleteById(id: Int){
        taskRepository.deleteById(id)
    }

    fun update(task: Task){
        taskRepository.update(task)
    }

}