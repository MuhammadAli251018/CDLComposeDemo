package com.androidnerds.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.androidnerds.core.Screen

class BaseNavigator(
    initialDestination: BaseNavigatorScreen.Home = DefaultHomeScreen()
) : Navigator(initialDestination) {
    override val id: NavigatorId = NavigatorId("base_navigator")
}

interface BaseNavigatorScreen : Screen {
    interface Home : BaseNavigatorScreen
}

class DefaultHomeScreen : BaseNavigatorScreen.Home {
    override val id: NavigationDestinationId
        get() = NavigationDestinationId("default_home_screen")

    @Composable
    override fun Content() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text("Default Home Screen")

            val navigationManager = LocalNavigationManager.current

            Button(onClick = { navigationManager.navigateTo(AlternativeHomeScreen()) }) {
                Text("Go to Alternative Home Screen")
            }
        }
    }
}

class AlternativeHomeScreen : BaseNavigatorScreen.Home {
    override val id: NavigationDestinationId
        get() = NavigationDestinationId("alternative_home_screen")

    @Composable
    override fun Content() {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text("Alternative Home Screen")

            val navigationManager = LocalNavigationManager.current

            Button(onClick = {
                navigationManager.navigateBack()
            }) {
                Text("Go Back to Default Home Screen")
            }
        }
    }
}