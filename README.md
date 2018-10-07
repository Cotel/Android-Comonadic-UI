## Android Comonadic UI

An Android application implemented in a declarative way bringing some concepts from [Comonadic Declarative UIs](https://functorial.com/the-future-is-comonadic/main.pdf) using [Λrrow](http://arrow-kt.io).


### What is a Comonad?

Comonads are the dual of [Monads](https://arrow-kt.io/docs/patterns/monads/).

A Comonad represents a Space of points. We can consider a UI as a Space of states. So we can build a UI with a Comonad as it is a Space of all possible points, and user actions are movements inside this Space.

For example, `Store` is a comonadic datatype which holds a state and a render function (lambda) for giving a visual representation of its state. We can `extract()` the Comonad which means giving a representation of the current point in the Space. We can also `move(newState)` the Comonad inside the Space.

```kotlin
class MainActivity : Activity() {

    var greetingComponent: Store<String, Unit> by observable(Store(initialState = "John Doe") { state ->
        user_name_textview.text = state
    }) { _, _, new -> new.extract() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        greetingComponent.extract()                                 // The textview should show "John Doe"
        greetingComponent = greetingComponent.move("A new name")    // The textview should show "A new name"
      }
}
```

This declarative approach is similar to other libraries and languages for frontend applications like React, Elm, Halogen, etc.

:medal_sports: The Future is Comonadic :medal_sports:


### Scope

This app is a classical TODOs manager example. The core features are:

* Showing a list of TODOs. If there are no tasks then an empty case should be shown.
* Marking a TODO as completed. It's title should be struck through.
* Deleting a TODO.
* Creating new TODOs.

In the future we will add more features for showing how we can interact with the Android framework with a declarative approach.
