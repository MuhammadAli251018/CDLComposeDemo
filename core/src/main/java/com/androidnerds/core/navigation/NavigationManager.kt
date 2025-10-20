package com.androidnerds.core.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface NavigationManager {
    fun navigateTo(navigator: Navigator, screen: AppScreen)
    fun navigateBack(navigator: Navigator)
}


// Todo: I'm not sure if i should force an initial screen in the stack or when creating a navigator
data class NavigationStackState(val screens: List<AppScreen>) {

    fun push(screen: AppScreen): NavigationStackState {
        val newStack = screens.toMutableList().apply { add(screen) }
        return NavigationStackState(newStack)
    }

    fun pop(): NavigationStackState {
        if (screens.isEmpty()) return this
        val newStack = screens.toMutableList().apply { removeAt(lastIndex) }
        return NavigationStackState(newStack)
    }

    fun peek(reverseIndex: Int = 0): AppScreen? {
        val index = screens.size - 1 - reverseIndex
        return if (index in screens.indices) screens[index] else null
    }
}

class NavigationManagerImpl : NavigationManager {
    private val _navigationStacks = MutableStateFlow<Map<Navigator, NavigationStackState>>(emptyMap())
    val navigationStacks: StateFlow<Map<Navigator, NavigationStackState>> =
        _navigationStacks.asStateFlow()

    override fun navigateTo(navigator: Navigator, screen: AppScreen) {
        _navigationStacks.update {
            val stack = it[navigator] ?: NavigationStackState(emptyList())
            it + (navigator to stack.push(screen))
        }
    }

    override fun navigateBack(navigator: Navigator) {
        _navigationStacks.update {
            val stack = it[navigator] ?: NavigationStackState(emptyList())
            it + (navigator to stack.pop())
        }
    }
}