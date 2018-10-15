package cotel.comonadic_ui

import java.util.*

data class Todo(
  val title: String,
  val id: UUID = UUID.randomUUID(),
  val isCompleted: Boolean = false
)

