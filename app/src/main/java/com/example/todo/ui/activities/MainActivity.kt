package com.example.todo.ui.activities

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajts.androidmads.library.SQLiteToExcel
import com.ajts.androidmads.library.SQLiteToExcel.ExportListener
import com.example.todo.databinding.ActivityMainBinding
import com.example.todo.databinding.AddTodoBinding
import com.example.todo.db.Todo
import com.example.todo.other.Constants
import com.example.todo.ui.adapters.TodoAdapter
import com.example.todo.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), TodoAdapter.TodoListener {

    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private lateinit var todoAdapterComplete: TodoAdapter
    private lateinit var todoAdapterInComplete: TodoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            todoAdapterComplete = TodoAdapter(this@MainActivity)
            todoAdapterInComplete = TodoAdapter(this@MainActivity)


            fab.setOnClickListener {
                addTodo()
            }

            rvTodoComplete.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = todoAdapterComplete
            }

            rvTodoIncomplete.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = todoAdapterInComplete
            }

            export.setOnClickListener {
                val sqliteToExcel = SQLiteToExcel(this@MainActivity, Constants.DB_NAME)

                sqliteToExcel.exportAllTables("table1.xls", object : ExportListener {
                    override fun onStart() {
                        Toast.makeText(this@MainActivity, "Exporting...", Toast.LENGTH_SHORT).show()
                    }
                    override fun onCompleted(filePath: String) {
                        Log.d("TAG", "onCompleted: file saved in $filePath")
                        Toast.makeText(this@MainActivity, "File Exported", Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Exception) {
                        e.printStackTrace()
                        Log.d("TAG", "onError: error occured: ${e.message}")
                        Toast.makeText(this@MainActivity, "Error Occurred", Toast.LENGTH_SHORT).show()
                    }
                })

            }

        }

        setObserver()

    }

    private fun setObserver() {
        vm.completeTodo.observe(this, Observer {
            todoAdapterComplete.submitList(it)
        })

        vm.incompleteTodo.observe(this, Observer {
            todoAdapterInComplete.submitList(it)
        })
    }

    private fun addTodo() {
        val dialog = Dialog(this)
        val binding = AddTodoBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.apply {
            btnCancel.setOnClickListener {
                dialog.cancel()
            }

            btnSave.setOnClickListener {
                val taskName = etTask.text.toString()

                if(taskName.isEmpty()){
                    Toast.makeText(this@MainActivity, "Please enter a task", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                val creationTime = System.currentTimeMillis()
                val todo = Todo(id = "$creationTime", task = taskName, isCompleted = false, creationTime = creationTime)
                vm.upsertTodo(todo)
                dialog.cancel()
            }
        }

        dialog.show()

        val window = dialog.window

        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

    }

    override fun todoUpdated(todo: Todo) {
        vm.upsertTodo(todo)
    }

    override fun deleteTodoById(id: String) {
        vm.deleteTodoById(id)
    }
}