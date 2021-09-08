package com.github.douglasbastos25.todolist.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.github.douglasbastos25.todolist.App
import com.github.douglasbastos25.todolist.R
import com.github.douglasbastos25.todolist.databinding.ActivityMainBinding
import com.github.douglasbastos25.todolist.presentation.MainViewModel
import com.github.douglasbastos25.todolist.presentation.MainViewModelFactory
import com.github.douglasbastos25.todolist.ui.AddTaskActivity.Companion.TASK_ID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TaskListAdapter() }
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvTasks.adapter = adapter

        getAllTasks()

        insertListeners()

    }

    private fun getAllTasks() {
        mainViewModel.getAll().observe(this, { tasks ->
            if (tasks.isEmpty()) {
                adapter.submitList(arrayListOf())
                binding.rvTasks.visibility = View.INVISIBLE
                binding.includeEmpty.emptyState.visibility = View.VISIBLE
            } else {
                binding.includeEmpty.emptyState.visibility = View.INVISIBLE
                binding.rvTasks.visibility = View.VISIBLE
                adapter.submitList(tasks)
            }
        })
    }


    private fun insertListeners() {
        binding.fbAdd.setOnClickListener {
            startActivity(Intent(this@MainActivity, AddTaskActivity::class.java))
        }

        adapter.listenerEdit = {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra(TASK_ID, it.id)
            startActivity(intent)
        }

        adapter.listenerDelete = {
            mainViewModel.deleteById(it.id)
            Toast.makeText(this@MainActivity, getString(R.string.task_deleted), Toast.LENGTH_SHORT)
                .show()

        }
    }

}