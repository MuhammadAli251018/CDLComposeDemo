package com.androidnerds.plugin.screenb

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
import com.androidnerds.core.navigation.AppScreen

class ScreenB : Screen {
    @Composable
    override fun Content() {
        var counter by remember { mutableIntStateOf(0) }

        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                "Screen B: Plus Counter $counter",
                modifier = Modifier.clickable { counter ++ }
            )
        }
    }
}