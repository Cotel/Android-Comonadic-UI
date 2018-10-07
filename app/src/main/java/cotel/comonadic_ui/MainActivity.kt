package cotel.comonadic_ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
  TodosListFragment.TodosListDelegate,
  NewTodoFragment.NewTodoDelegate {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    supportFragmentManager.beginTransaction()
      .replace(R.id.fragment_container, TodosListFragment.newInstance())
      .commit()
  }

  override fun onTodosListAttached() {
    toolbar.title = getString(R.string.app_name)
    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
  }

  override fun onNewTodoAttached() {
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener {
      supportFragmentManager!!.popBackStack()
    }
    toolbar.title = "New Todo"
  }
}
