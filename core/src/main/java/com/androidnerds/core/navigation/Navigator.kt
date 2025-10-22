package com.androidnerds.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.androidnerds.core.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class NavigatorId(val id: String)

@Serializable
@JvmInline
value class NavigationDestinationId(val id: String)

data class StackState <E> (val elements: List<E>) {
    constructor() : this(emptyList())
    constructor(vararg screens: E) : this(screens.toList())

    fun push(element: E): StackState<E> {
        val newStack = elements.toMutableList().apply { add(element) }
        return StackState(newStack)
    }

    fun pop(removedElementProvider: (E) -> Unit = {}): StackState<E> {
        if (elements.isEmpty()) return this
        val newStack = elements.toMutableList().apply {
            removedElementProvider(removeAt(lastIndex))
        }
        return StackState(newStack)
    }

    fun popIfNotLast(removedElementProvider: (E) -> Unit = {}): StackState<E> {
        if (elements.size <= 1) return this
        return pop(removedElementProvider)
    }

    fun peek(index: Int? = null): E? {
        val idx = index ?: elements.lastIndex
        return if (idx in elements.indices) elements[idx] else null
    }
}

abstract class Navigator(protected val initialDestination: Screen) {
    abstract val id: NavigatorId
    private val _currentScreen = MutableStateFlow(initialDestination)

    private var backStack = StackState(initialDestination)
        set(value) {
            field = value
            _currentScreen.value = value.peek() ?: initialDestination
        }

    val currentDestination = _currentScreen.asStateFlow()

    protected fun updateBackStack(update: StackState<Screen>.() -> StackState<Screen>) {
        backStack = backStack.update()
    }

    fun navigateTo(destination: Screen) {
        updateBackStack { push(destination) }
    }

    fun navigateToAndClearStack(destination: Screen) {
        backStack = StackState(destination)
    }

    fun popToRoot() {
        if (backStack.elements.isNotEmpty()) {
            backStack = StackState(initialDestination)
        }
    }

    fun popTo(destination: Screen): Boolean {
        val targetIndex = backStack.elements.indexOfLast { it.id == destination.id }
        return if (targetIndex >= 0) {
            val newElements = backStack.elements.take(targetIndex + 1)
            backStack = StackState(newElements)
            true
        } else {
            false
        }
    }

    fun navigateBack(): Boolean {
        val previousStack = backStack
        updateBackStack { popIfNotLast() }
        return previousStack != backStack
    }

    // Todo: Add support for transitions between screens
    @Composable
    fun ComposableNavigator() {
        val currentScreen by _currentScreen.collectAsStateWithLifecycle()
        currentScreen.Content()
    }
}