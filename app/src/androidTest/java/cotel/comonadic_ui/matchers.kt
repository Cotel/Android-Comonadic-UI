package cotel.comonadic_ui

import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.BoundedMatcher
import android.view.View
import android.widget.TextView
import com.agoda.kakao.TextViewAssertions
import org.hamcrest.Description
import org.hamcrest.Matcher

fun withPaintFlag(paintFlags: Int): Matcher<View> =
  object : BoundedMatcher<View, TextView>(TextView::class.java) {
    override fun describeTo(description: Description) {
      description.appendText("with paint flag: ")
    }

    override fun matchesSafely(item: TextView): Boolean =
      (item.paintFlags and paintFlags) == paintFlags
  }

fun TextViewAssertions.withPaintFlag(paintFlags: Int) {
  view.check(ViewAssertions.matches(cotel.comonadic_ui.withPaintFlag(paintFlags)))
}