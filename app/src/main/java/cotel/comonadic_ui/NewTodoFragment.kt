package cotel.comonadic_ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cotel.comonadic_ui.usecases.AddTodo
import kotlinx.android.synthetic.main.fragment_new_todo.*

class NewTodoFragment : Fragment(), NewTodoPresenter.View {

  companion object {
    fun newInstance(): NewTodoFragment = NewTodoFragment()
  }

  interface NewTodoDelegate {
    fun onNewTodoAttached()
  }

  private val addTodo = AddTodo(ServiceLocator.todosRepository)
  private val presenter = NewTodoPresenter(this, addTodo)

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_new_todo, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    presenter.onCreate()
  }

  override fun onResume() {
    super.onResume()

    (activity as NewTodoDelegate).onNewTodoAttached()
  }

  override fun setupUI() {
    submit_todo_button.setOnClickListener { handleSubmitPressed() }
  }

  private fun handleSubmitPressed() {
    presenter.handleSubmitTodo(todo_title_input.text.toString())
  }

  override fun showEmptyTitleError() {
    todo_title_input.error = "Todo title cannot be empty"
  }

  override fun clearErrors() {
    todo_title_input.error = null
  }

  override fun exit() {
    fragmentManager!!.popBackStack()
  }
}
