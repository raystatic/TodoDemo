package com.example.todo.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.db.Todo
import kotlinx.android.synthetic.main.todo_item.view.*
import java.util.*

class TodoAdapter(var listener: TodoListener) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    inner class TodoViewHolder(itemView:View): RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<Todo>(){
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean = oldItem.hashCode() == newItem.hashCode()
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Todo>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder =
        TodoViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.todo_item, parent, false)
        )

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {

        val item = differ.currentList[position]

        holder.itemView.apply {
            Log.d("todo","item: $item")
            cbTodo.isChecked = item.isCompleted
            cbTodo.text = item.task

            cbTodo.setOnCheckedChangeListener { buttonView, isChecked ->
                item.isCompleted = isChecked
                listener.todoUpdated(item)
            }

            imgDelete.setOnClickListener {
                listener.deleteTodoById(item.id)
            }

        }

    }

    interface TodoListener{
        fun todoUpdated(todo: Todo)
        fun deleteTodoById(id:String)
    }
}