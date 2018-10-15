package cotel.comonadic_ui.usecases

import cotel.comonadic_ui.TodosRepository

class GetAllTodos(
  private val todosRepository: TodosRepository
) {
  operator fun invoke() = todosRepository.getAllTodos()
}