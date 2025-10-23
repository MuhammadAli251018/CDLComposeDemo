package com.androidnerds.core.navigation

import com.androidnerds.core.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

interface NavigationManager {
    val currentNavigator: StateFlow<Navigator>

    fun navigateTo(screen: Screen)
    fun startNavigator(navigator: Navigator)
    fun navigateBack()
    fun startNewNavigator(navigator: Navigator)
}

class NavigationManagerImpl(
    private val coroutineScope: CoroutineScope,
    private val baseNavigator: BaseNavigator
) : NavigationManager {

    private val _currentNavigator = MutableStateFlow<Navigator>(baseNavigator)
    override val currentNavigator: StateFlow<Navigator> = _currentNavigator.asStateFlow()

    private val navigatorStack = mutableListOf<Navigator>(baseNavigator)

    override fun navigateTo(screen: Screen) {
        coroutineScope.launch {
            _currentNavigator.value.navigateTo(screen)
        }
    }

    override fun startNavigator(navigator: Navigator) {
        coroutineScope.launch {
            if (_currentNavigator.value.id != navigator.id) {
                navigatorStack.add(navigator)
                _currentNavigator.value = navigator
            }
        }
    }

    override fun navigateBack() {
        coroutineScope.launch {
            val currentNav = _currentNavigator.value
            val didNavigateBack = currentNav.navigateBack()

            if (!didNavigateBack && navigatorStack.size > 1) {
                navigatorStack.removeLastOrNull()
                val previousNavigator = navigatorStack.lastOrNull()
                if (previousNavigator != null) {
                    _currentNavigator.value = previousNavigator
                }
            }
        }
    }

    override fun startNewNavigator(navigator: Navigator) {
        coroutineScope.launch {
            navigatorStack.clear()
            navigatorStack.add(baseNavigator)

            if (navigator.id != baseNavigator.id) {
                navigatorStack.add(navigator)
                _currentNavigator.value = navigator
            } else {
                _currentNavigator.value = baseNavigator
            }
        }
    }
}
