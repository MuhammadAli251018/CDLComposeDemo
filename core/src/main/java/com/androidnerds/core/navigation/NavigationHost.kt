package com.androidnerds.core.navigation

import androidx.compose.runtime.Composable

@Composable
fun NavigationHost(navigator: Navigator) {
    navigator.CurrentScreen()
}

@Composable
fun BaseNavigationHost() {
    val baseNavigator = currentBaseNavigator()
    NavigationHost(baseNavigator)
}
