package com.androidnerds.cdlcomposedemo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import com.androidnerds.core.Screen
import com.androidnerds.core.navigation.LocalNavigationManager
import com.androidnerds.core.navigation.NavigationDestinationId

class ScreenA : Screen {

    @Composable
    override fun Content() {
        Column(
            Modifier.fillMaxSize().background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            var counter by remember { mutableIntStateOf(0) }

            LaunchedEffect (Unit) {
                while (true) {
                    kotlinx.coroutines.delay(1000)
                    counter++
                }
            }
            Text("Screen A: $counter", fontSize = 24.sp)

            val navigationManager = LocalNavigationManager.current

            Button(onClick = { navigationManager.navigateTo(ScreenB()) }) {
                Text("Go to Screen B")
            }

            Button(onClick = { navigationManager.navigateBack() }) {
                Text("Go Back")
            }
        }
    }

    override val id = NavigationDestinationId("screen_a")
}

class ScreenB : Screen {

    @Composable
    override fun Content() {
        Column(
            Modifier.fillMaxSize().background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            var counter by remember { mutableIntStateOf(0) }

            LaunchedEffect (Unit) {
                while (true) {
                    kotlinx.coroutines.delay(1000)
                    counter++
                }
            }

            Text("Screen B: $counter", fontSize = 24.sp)

            val navigationManager = LocalNavigationManager.current

            Button(onClick = { navigationManager.navigateTo(ScreenA()) }) {
                Text("Go to Screen A")
            }

            Button(onClick = { navigationManager.navigateBack() }) {
                Text("Go Back")
            }
        }
    }

    override val id = NavigationDestinationId("screen_b")
}

class VoyagerScreenA: cafe.adriel.voyager.core.screen.Screen {
    @Composable
    override fun Content() {
        Column(
            Modifier.fillMaxSize().background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            var counter by remember { mutableIntStateOf(0) }

            LaunchedEffect (Unit) {
                while (true) {
                    kotlinx.coroutines.delay(1000)
                    counter++
                }
            }
            Text("Screen A: $counter", fontSize = 24.sp)

            val navigationManager = LocalNavigator.current

            Button(onClick = { navigationManager?.push(VoyagerScreenB()) }) {
                Text("Go to Screen B")
            }

            Button(onClick = { navigationManager?.pop() }) {
                Text("Go Back")
            }
        }
    }

}

class VoyagerScreenB: cafe.adriel.voyager.core.screen.Screen {
    @Composable
    override fun Content() {
        Column(
            Modifier.fillMaxSize().background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            var counter by remember { mutableIntStateOf(0) }

            LaunchedEffect (Unit) {
                while (true) {
                    kotlinx.coroutines.delay(1000)
                    counter++
                }
            }

            Text("Screen B: $counter", fontSize = 24.sp)

            val navigationManager = LocalNavigator.current

            Button(onClick = { navigationManager?.push(VoyagerScreenA()) }) {
                Text("Go to Screen A")
            }

            Button(onClick = { navigationManager?.pop() }) {
                Text("Go Back")
            }
        }
    }
}