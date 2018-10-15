package cotel.comonadic_ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cotel.comonadic_ui.usecases.GetAllTodos
import cotel.comonadic_ui.usecases.RemoveTodo
import cotel.comonadic_ui.usecases.ToggleTodo
import kotlinx.android.synthetic.main.content_main.*

class TodosListFragment : Fragment(), TodosListPresenter.View {

  companion object {
    fun newInstance(): TodosListFragment = TodosListFragment()
  }

  interface TodosListDelegate {
    fun onTodosListAttached()
  }

  private val repository = ServiceLocator.todosRepository
  private val getAllTodos = GetAllTodos(repository)
  private val updateTodo = ToggleTodo(repository)
  private val removeTodo = RemoveTodo(repository)
  private val presenter = TodosListPresenter(this, getAllTodos, updateTodo, removeTodo)
  private val adapter = TodosAdapter(presenter::handleTodoPressed, presenter::handleTodoRemoved)

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.content_main, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    presenter.onCreate()
  }

  override fun onResume() {
    super.onResume()

    presenter.onResume()
  }

  override fun setupUI() {
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

  override fun showTodos(todos: List<Todo>) {
    adapter.setTodos(todos)
  }

  override fun showEmptyCase() {
    todos_empty_case.visibility = View.VISIBLE
  }

  override fun hideEmptyCase() {
    todos_empty_case.visibility = View.GONE
  }

  override fun updateTodo(todo: Todo) {
    adapter.updateTodo(todo)
  }

  override fun removeTodo(todo: Todo) {
    adapter.removeTodo(todo)
  }

  override fun addTodo(todo: Todo) {
    adapter.addTodo(todo)
  }
}
