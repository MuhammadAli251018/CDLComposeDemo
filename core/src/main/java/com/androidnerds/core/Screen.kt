package com.androidnerds.core

import androidx.compose.runtime.Composable
import com.androidnerds.core.navigation.NavigationDestinationId

interface NavigationDestination {
    val id: NavigationDestinationId
}

interface Screen : NavigationDestination {
    @Composable
    fun Content()
}