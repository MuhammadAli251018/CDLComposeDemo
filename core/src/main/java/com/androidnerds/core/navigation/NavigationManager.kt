package com.androidnerds.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.androidnerds.core.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface NavigationManager {
    val currentNavigator: StateFlow<Navigator>
    val registeredNavigators: StateFlow<Map<NavigatorId, Navigator>>

    fun navigateTo(screen: Screen)
    fun startNavigator(navigator: Navigator)
    fun switchToNavigator(navigatorId: NavigatorId)
    fun navigateBack()
    fun registerNavigator(navigator: Navigator)
    fun unregisterNavigator(navigatorId: NavigatorId)
    @Composable
    fun NavigationHost()
}

class NavigationManagerImpl(private val baseNavigator: BaseNavigator) : NavigationManager {

    private val _registeredNavigators = MutableStateFlow<Map<NavigatorId, Navigator>>(
        mapOf(baseNavigator.id to baseNavigator)
    )
    override val registeredNavigators: StateFlow<Map<NavigatorId, Navigator>> = _registeredNavigators.asStateFlow()

    private var navigatorsStack = StackState<Navigator>(baseNavigator)
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
        registerNavigator(navigator)
        navigatorsStack = navigatorsStack.push(navigator)
    }

    override fun switchToNavigator(navigatorId: NavigatorId) {
        val navigator = _registeredNavigators.value[navigatorId]
        if (navigator != null && currentNavigator.value.id != navigatorId) {
            navigatorsStack = navigatorsStack.push(navigator)
        }
    }

    override fun navigateBack() {
        val currentNavigator = currentNavigator.value
        if (currentNavigator.navigateBack().not() && navigatorsStack.elements.size > 1) {
            navigatorsStack = navigatorsStack.pop()
        }
    }

    override fun registerNavigator(navigator: Navigator) {
        _registeredNavigators.update { currentMap ->
            currentMap + (navigator.id to navigator)
        }
    }

    override fun unregisterNavigator(navigatorId: NavigatorId) {
        _registeredNavigators.update { currentMap ->
            currentMap - navigatorId
        }

        val updatedStack = StackState(navigatorsStack.elements.filter { it.id != navigatorId })
        if (updatedStack.elements.isNotEmpty()) {
            navigatorsStack = updatedStack
        }
    }

    @Composable
    override fun NavigationHost() {
        val currentNavigator by currentNavigator.collectAsStateWithLifecycle()
        currentNavigator.ComposableNavigator()
    }
}