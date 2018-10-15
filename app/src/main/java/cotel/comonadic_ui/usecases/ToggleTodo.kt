package cotel.comonadic_ui.usecases

import cotel.comonadic_ui.Todo
import cotel.comonadic_ui.TodosRepository

class ToggleTodo(
  private val repository: TodosRepository
) {
  operator fun invoke(todo: Todo): Todo {
    val toggledTodo = todo.copy(isCompleted = !todo.isCompleted)
    repository.updateTodo(toggledTodo)
    return toggledTodo
  }
}