package cotel.comonadic_ui

class TodosRepository {

  private val list: MutableList<Todo> = mutableListOf()

  fun getAllTodos(): List<Todo> = list.toList() // We need to use `toList()` because otherwise
                                                // the reference of the list is returned
                                                // and immutability breaks

  fun addTodo(todo: Todo) {
    list.add(todo)
  }

  fun addAllTodos(todos: List<Todo>) {
    list.addAll(todos)
  }

  fun updateTodo(todo: Todo) {
    val index = list.indexOfFirst { it.id == todo.id }
    list[index] = todo
  }

  fun removeTodo(todo: Todo) {
    val index = list.indexOfFirst { it.id == todo.id }
    list.removeAt(index)
  }

}
