package com.androidnerds.core.navigation

import com.androidnerds.core.Screen

class BaseNavigator(
    override val initialDestination: BaseNavigatorScreen.Home
) : Navigator() {
    override val id: NavigatorId = NavigatorId("base_navigator")
}

interface BaseNavigatorScreen : Screen {
    interface Home : BaseNavigatorScreen
}