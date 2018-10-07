package cotel.comonadic_ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_new_todo.*

class NewTodoFragment : Fragment() {

  companion object {
    fun newInstance(): NewTodoFragment = NewTodoFragment()
  }

  interface NewTodoDelegate {
    fun onNewTodoAttached()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.fragment_new_todo, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    submit_todo_button.setOnClickListener { handleSubmitPressed() }
  }

  override fun onResume() {
    super.onResume()

    (activity as NewTodoDelegate).onNewTodoAttached()
  }

  private fun handleSubmitPressed() {
    val todoTitle = todo_title_input.text.toString()

    if (todoTitle.isNotBlank()) {
      val currentState = ServiceLocator.todosState
      ServiceLocator.todosState = currentState
        .copy(todos = currentState.todos + Todo(todoTitle))

      fragmentManager!!.popBackStack()
    }
  }

}
