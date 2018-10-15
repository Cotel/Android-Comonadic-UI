package cotel.comonadic_ui

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_todo.view.*

class TodosAdapter(
  private val onItemClick: (Todo) -> Unit,
  private val onRemoveClick: (Todo) -> Unit
) :
  RecyclerView.Adapter<TodosAdapter.ViewHolder>() {

  private val todos: MutableList<Todo> = mutableListOf()

  fun setTodos(todos: List<Todo>) {
    this.todos.clear()
    this.todos.addAll(todos)
    notifyDataSetChanged()
  }

  fun updateTodo(todo: Todo) {
    val todoIndex = todos.indexOfFirst { it.id == todo.id }
    todos[todoIndex] = todo
    notifyItemChanged(todoIndex)
  }

  fun removeTodo(todo: Todo) {
    val todoIndex = todos.indexOfFirst { it.id == todo.id }
    todos.removeAt(todoIndex)
    notifyItemRemoved(todoIndex)
  }

  fun addTodo(todo: Todo) {
    todos.add(todo)
    notifyItemInserted(todos.size - 1)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(
      LayoutInflater.from(parent.context)
        .inflate(R.layout.item_todo, parent, false)
    )

  override fun getItemCount(): Int = todos.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(todos[position], onItemClick, onRemoveClick)
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(todo: Todo, onItemClick: (Todo) -> Unit, onRemoveClick: (Todo) -> Unit) {
      with(itemView) {
        todo_title.text = todo.title

        if (todo.isCompleted) {
          todo_title.paintFlags = todo_title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
          todo_title.paintFlags = 0
        }

        setOnClickListener { onItemClick(todo) }
        todo_remove_button.setOnClickListener { onRemoveClick(todo) }
      }
    }
  }
}