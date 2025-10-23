package com.androidnerds.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun currentNavigator(): Navigator {
    val navigationManager = LocalNavigationManager.current
    val navigator by navigationManager.currentNavigator.collectAsStateWithLifecycle()
    return navigator
}

fun NavigationManager.navigateBackToRoot() {
    while (true) {
        val currentNavigator = currentNavigator.value
        if (!currentNavigator.navigateBack()) {
            break
        }
    }
}

inline fun <reified T : Navigator> NavigationManager.currentNavigatorAsOrNull(): T? =
    currentNavigator.value as? T

@Composable
fun currentBaseNavigator(): BaseNavigator {
    val navigationManager = LocalNavigationManager.current
    return remember {
        navigationManager
            .currentNavigatorAsOrNull<BaseNavigator>() ?: error("BaseNavigator not found in NavigationManager")
    }
}

@Composable
fun rememberBaseNavigator(homeScreen: BaseNavigatorScreen.Home): BaseNavigator {
    return remember { BaseNavigator(initialDestination = homeScreen) }
}