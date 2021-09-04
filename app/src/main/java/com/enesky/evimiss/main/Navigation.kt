package com.enesky.evimiss.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.enesky.evimiss.ui.scaffold.bottomNav.BottomNavItem
import com.enesky.evimiss.ui.screens.calendar.CalendarScreen
import com.enesky.evimiss.ui.screens.MoreScreen
import com.enesky.evimiss.ui.screens.NotesScreen
import com.enesky.evimiss.ui.screens.calendar.CalendarViewModel

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Calendar.route) {
        composable(BottomNavItem.Notes.route) { NotesScreen() }
        composable(BottomNavItem.Calendar.route) { CalendarScreen(calendarViewModel = CalendarViewModel()) }
        composable(BottomNavItem.More.route) { MoreScreen() }
    }
}