package com.example.todolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView);

    fun addTodo(todo: Todo) {
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos() {
        todos.removeAll { todo ->
            todo.isChecked
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
            return TodoViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_todo,
                    parent,
                    false)
            )
    }

    private fun toggleStrikeThrough(tvTodoTitle: TextView, isChecked: Boolean) {
        if (isChecked) {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodoTitle.paintFlags = tvTodoTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val currTodo = todos[position]
        holder.itemView.apply {
            tvTodoTitle.text = currTodo.title
            cbDone.isChecked = currTodo.isChecked
            toggleStrikeThrough(tvTodoTitle, currTodo.isChecked)
            cbDone.setOnCheckedChangeListener { _, checked ->
                toggleStrikeThrough(tvTodoTitle, checked)
                currTodo.isChecked = !currTodo.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}