package com.androidnerds.core.navigation

@JvmInline
value class AppScreenId(val id: String)

interface AppScreen {
    val id: AppScreenId

    interface Home : AppScreen
    interface Settings : AppScreen
    interface Feedback : AppScreen
}