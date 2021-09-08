package com.github.douglasbastos25.todolist.datasource

import androidx.lifecycle.LiveData
import androidx.room.*
import com.github.douglasbastos25.todolist.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    fun getAll(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id = :id")
    fun getTaskById(id: Int): LiveData<Task>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM Task WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Update
    suspend fun update(task: Task)

}