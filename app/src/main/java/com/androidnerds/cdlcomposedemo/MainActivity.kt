package com.androidnerds.cdlcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.androidnerds.cdlcomposedemo.ui.theme.CDLComposeDemoTheme
import com.androidnerds.core.navigation.NavigationProvider
import com.androidnerds.core.navigation.rememberNavigationManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CDLComposeDemoTheme {
                val navigationManager = rememberNavigationManager()
                NavigationProvider(navigationManager = navigationManager) {
                        navigationManager.NavigationHost()
                }
            }
        }
    }
}