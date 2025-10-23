package com.androidnerds.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

val LocalNavigationManager = compositionLocalOf<NavigationManager> {
    error("NavigationManager not provided")
}

@Composable
fun NavigationProvider(
    navigationManager: NavigationManager,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalNavigationManager provides navigationManager,
        content = content
    )
}

// Todo: Navigation Manager should be singleton per app
@Composable
fun rememberNavigationManager(
    baseNavigator: BaseNavigator = remember { BaseNavigator() }
): NavigationManager {
    return remember { NavigationManagerImpl(CoroutineScope(Dispatchers.IO), baseNavigator) }
}
