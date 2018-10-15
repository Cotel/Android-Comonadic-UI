package cotel.comonadic_ui

import cotel.comonadic_ui.usecases.AddTodo

class NewTodoPresenter(
  private val view: View,
  private val addTodo: AddTodo
) {
  interface View {
    fun setupUI()
    fun showEmptyTitleError()
    fun clearErrors()
    fun exit()
  }

  fun onCreate() {
    view.setupUI()
  }

  fun handleSubmitTodo(todoTitle: String) {
    view.clearErrors()
    if (todoTitle.isNotBlank()) {
      addTodo(Todo(todoTitle))
      view.exit()
    } else {
      view.showEmptyTitleError()
    }
  }
}
