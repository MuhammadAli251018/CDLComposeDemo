package com.androidnerds.core.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.androidnerds.core.Screen

class BaseNavigator(
    initialDestination: BaseNavigatorScreen.Home = DefaultHomeScreen()
) : Navigator(initialDestination) {
    companion object {
        const val BASE_NAVIGATOR_ID = "base_navigator"
    }
    override val id: NavigatorId = NavigatorId(BASE_NAVIGATOR_ID)
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
        }
    }
}