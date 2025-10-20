package com.androidnerds.core.navigation

import kotlinx.serialization.Serializable

@Serializable
@JvmInline
value class NavigatorId(val id: String)

interface Navigator {
    val id: NavigatorId
}

object BaseNavigator : Navigator {
    override val id: NavigatorId = NavigatorId("base_navigator")
}

data class DynamicNavigator(override val id: NavigatorId) : Navigator