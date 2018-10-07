package cotel.comonadic_ui.datatypes

class Store<S, V>(val state: S, val render: (S) -> V) {

  fun extract(): V = render(state)

  fun <A> extend(f: (Store<S, V>) -> A): Store<S, A> =
      Store(state) { next -> f(Store(next, render)) }

  fun <A> map(f: (V) -> A): Store<S, A> =
      Store(state) { next -> f(render(next)) }

  fun duplicate(): Store<S, Store<S, V>> = extend { it }

  fun move(newState: S): Store<S, V> = Store(newState, render)

}