package cotel.comonadic_ui.datatypes

class Moore<E, S>(val state: S, val handle: (E) -> Moore<E, S>) {

  fun extract(): S = state

  fun <A> extend(f: (Moore<E, S>) -> A): Moore<E, A> =
    Moore(f(Moore(state, handle))) { update ->
      handle(update).extend(f)
    }

  fun <A> map(f: (S) -> A): Moore<E, A> =
    Moore(f(state)) { update -> handle(update).map(f) }

  fun duplicate(): Moore<E, Moore<E, S>> = extend { it }

}