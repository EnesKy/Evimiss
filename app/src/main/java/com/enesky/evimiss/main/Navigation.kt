package com.enesky.evimiss.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.enesky.evimiss.ui.scaffold.bottomNav.BottomNavItem
import com.enesky.evimiss.ui.screens.SplashScreen
import com.enesky.evimiss.ui.screens.calendar.CalendarScreen
import com.enesky.evimiss.ui.screens.expense.ExpenseScreen
import com.enesky.evimiss.ui.screens.login.LoginScreen
import com.enesky.evimiss.ui.screens.main.MainScreen
import com.enesky.evimiss.ui.screens.notes.NotesScreen

const val SPLASH = "splash"
const val LOGIN = "login"
const val MAIN = "main"
const val CALENDAR = "calendar"
const val NOTES = "notes"
const val EXPENSE = "expense"

@Composable
fun BottomNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Calendar.route) {
        composable(BottomNavItem.Notes.route) { NotesScreen() }
        composable(BottomNavItem.Calendar.route) { CalendarScreen() }
        composable(BottomNavItem.Expense.route) { ExpenseScreen() }
    }
}

@Composable
fun NavigationFromSplash() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SPLASH) {
        composable(SPLASH) { SplashScreen(navController) }
        composable(LOGIN) { LoginScreen(navController) }
        composable(MAIN) { MainScreen() }
    }
}