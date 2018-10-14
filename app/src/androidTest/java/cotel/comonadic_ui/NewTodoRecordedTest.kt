package cotel.comonadic_ui

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.closeSoftKeyboard
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NewTodoRecordedTest {

  @Rule
  @JvmField
  var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

  @Test
  fun should_add_a_new_todo_to_the_list_after_navigating_to_creation_screen() {
    val floatingActionButton = onView(
      allOf(withId(R.id.fab),
        childAtPosition(
          childAtPosition(
            withId(R.id.fragment_container),
            0),
          2),
        isDisplayed()))
    floatingActionButton.perform(click())

    val textInputEditText = onView(
      allOf(withId(R.id.todo_title_input),
        childAtPosition(
          childAtPosition(
            withId(R.id.todo_title_input_layout),
            0),
          0),
        isDisplayed()))
    textInputEditText.perform(replaceText("test todo"), closeSoftKeyboard())

    val appCompatButton = onView(
      allOf(withId(R.id.submit_todo_button), withText("Create todo"),
        childAtPosition(
          childAtPosition(
            withId(R.id.fragment_container),
            0),
          2),
        isDisplayed()))
    appCompatButton.perform(click())

    val recyclerView = onView(
      allOf(withId(R.id.todos_list),
        childAtPosition(
          childAtPosition(
            withId(R.id.fragment_container),
            0),
          0),
        isDisplayed()))
    recyclerView.check(matches(isDisplayed()))

    val textView = onView(
      allOf(withId(R.id.todo_title), withText("test todo"),
        childAtPosition(
          childAtPosition(
            withId(R.id.todos_list),
            0),
          0),
        isDisplayed()))
    textView.check(matches(withText("test todo")))
  }

  private fun childAtPosition(
    parentMatcher: Matcher<View>, position: Int): Matcher<View> {

    return object : TypeSafeMatcher<View>() {
      override fun describeTo(description: Description) {
        description.appendText("Child at position $position in parent ")
        parentMatcher.describeTo(description)
      }

      public override fun matchesSafely(view: View): Boolean {
        val parent = view.parent
        return parent is ViewGroup && parentMatcher.matches(parent)
          && view == parent.getChildAt(position)
      }
    }
  }
}
