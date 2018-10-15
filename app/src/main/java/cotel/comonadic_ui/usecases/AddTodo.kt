package cotel.comonadic_ui.usecases

import cotel.comonadic_ui.Todo
import cotel.comonadic_ui.TodosRepository

class AddTodo(private val repository: TodosRepository) {
  operator fun invoke(todo: Todo) {
    repository.addTodo(todo)
  }
}