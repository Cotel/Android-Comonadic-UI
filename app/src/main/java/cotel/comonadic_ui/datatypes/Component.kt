package cotel.comonadic_ui.datatypes

import arrow.data.State

data class Component<T>(val model: T, val render: (T) -> State<T, Unit>) {
  fun extract(): State<T, Unit> = render(model)

  fun move(newModel: T): Component<T> = Component(newModel, render)
}