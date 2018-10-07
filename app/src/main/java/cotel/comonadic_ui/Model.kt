package cotel.comonadic_ui

import java.util.*

data class Todo(
  val title: String,
  val id: UUID = UUID.randomUUID(),
  val isCompleted: Boolean = false
)

data class TodosState(val todos: List<Todo>) {
  fun addTodo(todo: Todo): TodosState = copy(todos = todos + todo)

  fun updateTodo(todo: Todo): TodosState = copy(todos = todos.map {
    if (todo.id == it.id) todo else it
  })

  fun removeTodo(todo: Todo): TodosState = copy(todos = todos.filterNot { it.id == todo.id })
}

sealed class TodoInputs
class AddTodo(val todo: Todo) : TodoInputs()
class UpdateTodo(val todo: Todo) : TodoInputs()
class RemoveTodo(val todo: Todo) : TodoInputs()

