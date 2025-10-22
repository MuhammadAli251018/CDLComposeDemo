package com.androidnerds.cdlcomposedemo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.androidnerds.core.Screen
import com.androidnerds.core.navigation.NavigationDestinationId

class ScreenA : Screen {

    @Composable
    override fun Content() {
        var counter by remember { mutableIntStateOf(1000) }

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Screen A: Minus Counter $counter",
                modifier = Modifier.clickable { counter -- }
            )
        }
    }

    override val id = NavigationDestinationId("screen_a")
}