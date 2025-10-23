package com.androidnerds.cdlcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import com.androidnerds.cdlcomposedemo.ui.theme.CDLComposeDemoTheme
import com.androidnerds.core.navigation.BaseNavigationHost
import com.androidnerds.core.navigation.BaseNavigatorScreen
import com.androidnerds.core.navigation.NavigationDestinationId
import com.androidnerds.core.navigation.NavigationHost
import com.androidnerds.core.navigation.NavigationProvider
import com.androidnerds.core.navigation.Navigator
import com.androidnerds.core.navigation.NavigatorId
import com.androidnerds.core.navigation.rememberBaseNavigator
import com.androidnerds.core.navigation.rememberNavigationManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CDLComposeDemoTheme {
                val homeScreen = remember { AppHomeScreen() }
                val baseNavigator = rememberBaseNavigator(homeScreen)
                val navigationManager = rememberNavigationManager(baseNavigator)
                NavigationProvider(navigationManager = navigationManager) {
                    BaseNavigationHost()
                }

                /*cafe.adriel.voyager.navigator.Navigator(VoyagerAppHomeScreen())*/
            }
        }
    }
}

class AppHomeScreen : BaseNavigatorScreen.Home {
    override val id: NavigationDestinationId
        get() = NavigationDestinationId("app_home_screen")

    @Composable
    override fun Content() {
        val homeScreenNavigator = rememberHomeScreenNavigator()
        NavigationHost(navigator = homeScreenNavigator)
    }
}

class VoyagerAppHomeScreen : Screen{
    @Composable
    override fun Content() {
        cafe.adriel.voyager.navigator.Navigator(VoyagerScreenA())
    }
}

@Composable
private fun rememberHomeScreenNavigator(): Navigator {
    return remember { HomeScreenNavigator() }
}

class HomeScreenNavigator : Navigator(initialDestination = ScreenA()) {
    companion object {
        const val HOME_NAVIGATOR_ID = "app_home_navigator"
    }
    override val id: NavigatorId = NavigatorId(HOME_NAVIGATOR_ID)
}