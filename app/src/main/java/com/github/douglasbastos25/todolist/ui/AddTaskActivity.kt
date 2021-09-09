package com.github.douglasbastos25.todolist.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.douglasbastos25.todolist.App
import com.github.douglasbastos25.todolist.R
import com.github.douglasbastos25.todolist.databinding.ActivityAddTaskBinding
import com.github.douglasbastos25.todolist.extensions.format
import com.github.douglasbastos25.todolist.extensions.text
import com.github.douglasbastos25.todolist.model.Task
import com.github.douglasbastos25.todolist.presentation.MainViewModel
import com.github.douglasbastos25.todolist.presentation.MainViewModelFactory
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding
    private var taskForUpdate: Task? = null
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModelFactory((application as App).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveTask.text = getString(R.string.create_task)

        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            loadExistingData(taskId)
        }


        insertListeners()

    }

    private fun loadExistingData(taskId: Int) {
        mainViewModel.getTaskById(taskId).observe(this@AddTaskActivity, {
            taskForUpdate = it

            binding.tilTitle.text = taskForUpdate?.title.toString()
            binding.tilDate.text = taskForUpdate?.date.toString()
            binding.tilHour.text = taskForUpdate?.time.toString()
            binding.btnSaveTask.text = getString(R.string.update_task)
        })


    }

    private fun insertListeners() {
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.tilDate.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.tilDate.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER")
        }

        binding.tilHour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder().build()
            timePicker.addOnPositiveButtonClickListener {
                val time = "%02d:%02d".format(timePicker.hour, timePicker.minute)
                binding.tilHour.text = time
            }
            timePicker.show(supportFragmentManager, "TIME_PICKER")
        }

        binding.btnCancel.setOnClickListener { finish() }

        binding.btnSaveTask.setOnClickListener {
            if (binding.tilTitle.text.isNotBlank()) {
                if (taskForUpdate == null) {
                    mainViewModel.insert(
                        Task(
                            title = binding.tilTitle.text,
                            time = binding.tilHour.text,
                            date = binding.tilDate.text
                        )
                    )
                    Toast.makeText(
                        this@AddTaskActivity,
                        getString(R.string.task_created),
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()

                } else {
                    taskForUpdate?.let {

                        with(it) {
                            title = binding.tilTitle.text
                            time = binding.tilHour.text
                            date = binding.tilDate.text
                        }
                    }
                    mainViewModel.update(taskForUpdate!!)
                    Toast.makeText(this@AddTaskActivity, "Tasked Updated", Toast.LENGTH_SHORT)
                        .show()
                    finish()
                }
            } else {
                binding.tilTitle.error = getString(R.string.required_field)
            }
        }

        binding.inputTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.tilTitle.error = null
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }

    companion object {
        const val TASK_ID = "task_id"
    }
}