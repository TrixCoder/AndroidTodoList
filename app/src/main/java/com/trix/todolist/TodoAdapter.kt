package com.trix.todolist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.item_todo.view.*

class TodoAdapter(
    private val todos: MutableList<Todo>
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_todo,
                parent,
                false
            )
        )
    }

    fun addTodo(todo: Todo){
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    fun deleteDoneTodos(){
        todos.removeAll { todo->
            todo.isChecked
        }
        notifyDataSetChanged()
    }

    private fun toggleStrikeThrough(tvTodo: TextView, isChecked: Boolean){
        if(isChecked){
            tvTodo.paintFlags = tvTodo.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tvTodo.paintFlags = tvTodo.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val cTodos = todos[position]
        holder.itemView.apply {
            tvTodo.text = cTodos.title
            cbDone.isChecked = cTodos.isChecked
            toggleStrikeThrough(tvTodo, cTodos.isChecked)
            cbDone.setOnCheckedChangeListener{ _, isChecked ->
                toggleStrikeThrough(tvTodo, isChecked)
                cTodos.isChecked = !cTodos.isChecked
            }
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }
}