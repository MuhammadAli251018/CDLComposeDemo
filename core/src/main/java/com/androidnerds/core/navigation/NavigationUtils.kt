package com.androidnerds.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.androidnerds.core.Screen

/* Todo: Add utils & dsl after done
@Composable
fun currentNavigator(): Navigator {
    val navigationManager = LocalNavigationManager.current
    val navigator by navigationManager.currentNavigator.collectAsStateWithLifecycle()
    return navigator
}

@Composable
fun currentDestination(): Screen {
    val navigator = currentNavigator()
    val destination by navigator.currentDestination.collectAsStateWithLifecycle()
    return destination
}

fun NavigationManager.navigateToScreen(screen: Screen) = navigateTo(screen)

fun NavigationManager.switchNavigator(navigatorId: NavigatorId) = switchToNavigator(navigatorId)

fun NavigationManager.goBack() = navigateBack()

inline fun <reified T : Navigator> NavigationManager.getNavigator(id: NavigatorId): T? {
    return registeredNavigators.value[id] as? T
}

fun NavigationManager.hasNavigator(id: NavigatorId): Boolean {
    return registeredNavigators.value.containsKey(id)
}
*/
