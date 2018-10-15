package cotel.comonadic_ui

import cotel.comonadic_ui.datatypes.Store
import cotel.comonadic_ui.usecases.GetAllTodos
import cotel.comonadic_ui.usecases.RemoveTodo
import cotel.comonadic_ui.usecases.ToggleTodo

class TodosListPresenter(
  private val view: View,
  private val getAllTodos: GetAllTodos,
  private val toggleTodo: ToggleTodo,
  private val removeTodo: RemoveTodo
) {

  interface View {
    fun setupUI()
    fun showEmptyCase()
    fun hideEmptyCase()
    fun showTodos(todos: List<Todo>)
    fun updateTodo(todo: Todo)
    fun removeTodo(todo: Todo)
    fun addTodo(todo: Todo)
  }

  private var viewComponent = Store(emptyList<Todo>(), ::render)

  fun onCreate() {
    view.setupUI()
  }

  fun onResume() {
    fetchTodos()
  }

  private fun fetchTodos() {
    val todos = getAllTodos()
    moveViewComponent(todos)
  }

  fun handleTodoPressed(todo: Todo) {
    val result = toggleTodo(todo)
    val newList = viewComponent.state.map {
      if (it.id == result.id) result else it
    }
    moveViewComponent(newList)
  }

  fun handleTodoRemoved(todo: Todo) {
    removeTodo(todo)
    moveViewComponent(viewComponent.state - todo)
  }

  private fun moveViewComponent(newList: List<Todo>) {
    val newViewComponent = viewComponent.move(newList)
    newViewComponent.extract()
    viewComponent = newViewComponent
  }

  private fun render(newTodos: List<Todo>) {
    // Checking if the new state is empty for showing an empty case.
    if (newTodos.isEmpty()) {
      view.showEmptyCase()
      return
    }

    view.hideEmptyCase()

    val currentTodos = viewComponent.state

    // Checking which todos aren't in the new list so we can remove them.
    currentTodos
      .filterNot { todo -> newTodos.containsById(todo) }
      .forEach { view.removeTodo(it) }

    // Checking which todos are still in the new list but their completion state
    // has changed so we can update them.
    currentTodos
      .asSequence()
      .filter { todo -> newTodos.containsById(todo) }
      .map { todo ->
        val newTodo = newTodos.first { it.id == todo.id }
        newTodo
      }
      .toList()
      .forEach { view.updateTodo(it) }

    // Checking which todos from the new state aren't currently added so we add them to the list.
    val notAddedTodos = newTodos.filterNot { currentTodos.containsById(it) }
    notAddedTodos.forEach { view.addTodo(it) }
  }

  private fun List<Todo>.containsById(todo: Todo): Boolean =
    this.find { it.id == todo.id } != null
}