package com.enesky.evimiss.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enesky.evimiss.ui.custom.bottomNav.BottomNavigationBar
import com.enesky.evimiss.ui.screens.calendar.CalendarScreen
import com.enesky.evimiss.ui.screens.SplashScreen
import com.enesky.evimiss.ui.screens.calendar.CalendarViewModel
import com.enesky.evimiss.ui.theme.EvimissTheme
import com.enesky.evimiss.ui.theme.primary

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationFromSplash()
        }
    }
}

@Composable
fun Main() {
    val navController = rememberNavController()
    MainScaffold(
        content = { Navigation(navController = navController) },
        navController = navController
    )
}

@Composable
fun MainScaffold(content: @Composable () -> Unit, navController: NavController? = null) {
    EvimissTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = { BottomNavigationBar(navController) },
            backgroundColor = primary
        ) {
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    MainScaffold(
        content = { CalendarScreen(calendarViewModel = CalendarViewModel()) }
    )
}

@Composable
fun NavigationFromSplash() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController = navController) }
        composable("main") { Main() }
    }
}