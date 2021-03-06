package com.eky.evimiss.ui.scaffold.bottomNav

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eky.evimiss.ui.theme.Nunito
import com.eky.evimiss.ui.theme.primaryDark
import com.eky.evimiss.ui.theme.secondary
import com.eky.evimiss.ui.theme.secondaryLight

@Composable
fun BottomNav(navController: NavController? = null) {
    Surface(
        modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
        color = primaryDark
    ) {
        BottomNavigation(
            modifier = Modifier.padding(end = 72.dp),
            elevation = 0.dp,
            backgroundColor = primaryDark,
            contentColor = secondary
        ) {
            val currentRoute = if (navController != null) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                navBackStackEntry?.destination?.route
            } else
                null

            listOf(
                BottomNavItem.Notes,
                BottomNavItem.Calendar,
                BottomNavItem.Expense
            ).forEach { screen ->
                val isSelected = currentRoute == screen.route
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.title
                        )
                    },
                    label = {
                        Text(
                            text = screen.title.toString(),
                            style = TextStyle(
                                fontFamily = Nunito,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp,
                                letterSpacing = 0.6.sp
                            ),
                            textAlign = TextAlign.Center
                        )
                    },
                    selectedContentColor = secondaryLight,
                    unselectedContentColor = secondary,
                    alwaysShowLabel = false,
                    selected = isSelected,
                    onClick = {
                        navController?.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreview() {
    BottomNav(navController = rememberNavController())
}