package cotel.comonadic_ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cotel.comonadic_ui.datatypes.Moore
import cotel.comonadic_ui.datatypes.Store
import kotlinx.android.synthetic.main.content_main.*

class TodosListFragment : Fragment() {

  companion object {
    fun newInstance(): TodosListFragment = TodosListFragment()
  }

  interface TodosListDelegate {
    fun onTodosListAttached()
  }

  private fun handleUpdate(input: TodoInputs): Moore<TodoInputs, Store<TodosState, Unit>> =
    when (input) {
      is AddTodo -> {
        ServiceLocator.todosState = ServiceLocator.todosState
          .copy(todos = ServiceLocator.todosState.todos + input.todo)
        Moore(
          todosMoore.state.move(todosMoore.state.state.addTodo(input.todo)),
          ::handleUpdate
        )
      }

      is UpdateTodo -> {
        ServiceLocator.todosState = ServiceLocator.todosState
          .copy(todos = ServiceLocator.todosState.todos.map { todo ->
            if (todo.id == input.todo.id) input.todo else todo
          })
        Moore(
          todosMoore.state.move(todosMoore.state.state.updateTodo(input.todo)),
          ::handleUpdate
        )
      }

      is RemoveTodo -> {
        ServiceLocator.todosState = ServiceLocator.todosState
          .copy(todos = ServiceLocator.todosState.todos.filterNot { todo ->
            todo.id == input.todo.id
          })
        Moore(
          todosMoore.state.move(todosMoore.state.state.removeTodo(input.todo)),
          ::handleUpdate
        )
      }
    }

  private var todosMoore = Moore(Store(ServiceLocator.todosState) {
    adapter.setTodos(it.todos)
    if (it.todos.isNotEmpty()) {
      todos_empty_case.visibility = View.GONE
    } else {
      todos_empty_case.visibility = View.VISIBLE
    }
  }, ::handleUpdate)

  private val adapter = TodosAdapter(::handleTodoClicked, ::handleTodoRemoveClicked)

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.content_main, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    todos_list.let {
      it.adapter = adapter
      it.layoutManager = LinearLayoutManager(context)
      it.adapter.notifyDataSetChanged()
    }

    fab.setOnClickListener {
      fragmentManager!!.beginTransaction()
        .addToBackStack(null)
        .replace(R.id.fragment_container, NewTodoFragment.newInstance())
        .commit()
    }
  }

  override fun onResume() {
    super.onResume()

    (activity!! as TodosListDelegate).onTodosListAttached()
    (ServiceLocator.todosState.todos - todosMoore.state.state.todos).forEach {
      dispatch(AddTodo(it))
    }
    render()
  }

  private fun render() {
    todosMoore.extract().extract()
  }

  private fun dispatch(event: TodoInputs) {
    todosMoore = todosMoore.handle(event)
  }

  private fun handleTodoClicked(todo: Todo) {
    val newTodo = todo.copy(isCompleted = !todo.isCompleted)
    dispatch(UpdateTodo(newTodo))

    render()
  }

  private fun handleTodoRemoveClicked(todo: Todo) {
    dispatch(RemoveTodo(todo))

    render()
  }

}
