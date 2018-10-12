package cotel.comonadic_ui

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.agoda.kakao.KButton
import com.agoda.kakao.KRecyclerItem
import com.agoda.kakao.KRecyclerView
import com.agoda.kakao.KTextView
import com.agoda.kakao.Screen
import org.hamcrest.Matcher
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodosListFragmentTest {

  @Rule
  @JvmField
  val rule = ActivityTestRule(MainActivity::class.java, true, false)

  val todosListScreen = TodosListScreen()


  @Test
  fun should_show_empty_case_if_there_are_no_todos() {
    givenNoTodos()

    rule.launchActivity(null)

    todosListScreen {
      emptyCase {
        isVisible()
      }
    }
  }

  @Test
  fun should_show_two_items_if_there_are_two_todos() {
    givenTwoTodos()

    rule.launchActivity(null)

    todosListScreen {
      emptyCase { isNotDisplayed() }

      todosList {
        lastChild<TodosListScreen.TodoItem> {
          title { hasText("Another todo") }
        }
      }
    }
  }

  @Test
  fun should_mark_one_item_with_stroken_title_when_clicking_todo() {
    givenTwoTodos()

    rule.launchActivity(null)

    todosListScreen {
      todosList {
        firstChild<TodosListScreen.TodoItem> {
          click()

          title {
            hasText("One todo")

            // Dunno how to test paint flags ¯\_(ツ)_/¯
          }
        }
      }
    }

    assertTrue(ServiceLocator.todosState.todos.first().isCompleted)
  }

  @Test
  fun should_remove_one_item_if_todo_remove_button_is_pressed() {
    givenTwoTodos()

    rule.launchActivity(null)

    todosListScreen {
      todosList {
        firstChild<TodosListScreen.TodoItem> {
          removeBtn { click() }
        }

        hasSize(1)
      }
    }
  }

  private fun givenNoTodos() {
    ServiceLocator.todosState = TodosState(emptyList())
  }

  private fun givenTwoTodos() {
    ServiceLocator.todosState = TodosState(listOf(
      Todo("One todo"),
      Todo("Another todo")
    ))
  }

  class TodosListScreen : Screen<TodosListScreen>() {
    val emptyCase: KTextView = KTextView { withId(R.id.todos_empty_case) }
    val todosList: KRecyclerView = KRecyclerView(
      { withId(R.id.todos_list) },
      itemTypeBuilder = {
        itemType(::TodoItem)
      }
    )

    class TodoItem(parent: Matcher<View>) : KRecyclerItem<TodoItem>(parent) {
      val title: KTextView = KTextView(parent) { withId(R.id.todo_title) }
      val removeBtn: KButton = KButton(parent) { withId(R.id.todo_remove_button) }
    }
  }

}