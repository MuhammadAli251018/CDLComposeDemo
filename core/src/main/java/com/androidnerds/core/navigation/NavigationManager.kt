package com.androidnerds.core.navigation

import com.androidnerds.core.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface NavigationManager {
    val currentNavigator: StateFlow<Navigator>
    fun navigateTo(screen: Screen)
    fun startNavigator(navigator: Navigator)
    fun navigateBack()
}

class NavigationManagerImpl(private val baseNavigator: BaseNavigator) : NavigationManager {

    private var navigatorsStacks = StackState<Navigator>(baseNavigator)
        set(value) {
            field = value
            _currentNavigator.update { value.peek() ?: baseNavigator }
        }

    private val _currentNavigator = MutableStateFlow<Navigator>(baseNavigator)
    override val currentNavigator: StateFlow<Navigator> = _currentNavigator.asStateFlow()

    override fun navigateTo(screen: Screen) {
        currentNavigator.value.navigateTo(screen)
    }

    override fun startNavigator(navigator: Navigator) {
        navigatorsStacks = navigatorsStacks.push(navigator)
    }

    override fun navigateBack() {
        val currentNavigator = currentNavigator.value
        if (currentNavigator.navigateBack().not() && navigatorsStacks.elements.size > 1) {
            navigatorsStacks = navigatorsStacks.pop()
        }
    }
}