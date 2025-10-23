package com.androidnerds.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.androidnerds.core.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class NavigatorId(val id: String)

@Serializable
@JvmInline
value class NavigationDestinationId(val id: String)

data class StackState<E>(val elements: List<E>) {
    constructor() : this(emptyList())
    constructor(vararg screens: E) : this(screens.toList())

    fun push(element: E): StackState<E> {
        val newStack = elements.toMutableList().apply { add(element) }
        return StackState(newStack)
    }

    fun pushOrReplaceOnTop(element: E, getId: (E) -> Any): StackState<E> {
        val elementId = getId(element)
        val newStack = elements.toMutableList().apply {
            removeAll { getId(it) == elementId }
            add(element)
        }
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

    fun popAt(index: Int, removedElementProvider: (E) -> Unit = {}): StackState<E> {
        if (index !in elements.indices) return this
        val newStack = elements.toMutableList().apply {
            removedElementProvider(removeAt(index))
        }
        return StackState(newStack)
    }

    fun peek(index: Int? = null): E? {
        val idx = index ?: elements.lastIndex
        return if (idx in elements.indices) elements[idx] else null
    }
}

abstract class Navigator(val initialDestination: Screen) {
    abstract val id: NavigatorId

    private var backStack = MutableStateFlow(StackState(initialDestination))

    protected fun updateBackStack(update: StackState<Screen>.() -> StackState<Screen>) {
        backStack.update { it.update() }
    }

    fun navigateTo(destination: Screen) {
        updateBackStack { pushOrReplaceOnTop(destination) { it.id } }
    }

    fun popToRoot() {
        if (backStack.value.elements.isNotEmpty()) {
            backStack.update { StackState(initialDestination) }
        }
    }

    fun popTo(destination: Screen): Boolean {
        val targetIndex = backStack.value.elements.indexOfLast { it.id == destination.id }
        return if (targetIndex >= 0) {
            backStack.update {
                val newElements = it.elements.take(targetIndex + 1)
                StackState(newElements)
            }
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
    fun CurrentScreen() {
        val backStack by backStack.collectAsStateWithLifecycle()
        val currentScreen = backStack.peek()

        currentScreen?.Content()
    }
}