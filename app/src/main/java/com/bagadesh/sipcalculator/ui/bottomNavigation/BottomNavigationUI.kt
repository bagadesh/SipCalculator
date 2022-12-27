package com.bagadesh.sipcalculator.ui.bottomNavigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bagadesh.sipcalculator.R
import com.bagadesh.sipcalculator.main.bottomNavigationItems

/**
 * Created by bagadesh on 24/12/22.
 */


@Composable
fun BottomNavigationUI(navController: NavController) {
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        bottomNavigationItems.forEach { screen ->
            BottomNavigationItem(
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = getIconBasedOnScreen(screen)),
                        modifier = Modifier.size(22.dp),
                        contentDescription = null
                    )
                },
                label = { Text(text = screen.name, modifier = Modifier, fontSize = 10.sp, color = Color.White) }
            )
        }
    }
}

private fun getIconBasedOnScreen(screen: Screen): Int {
    return when (screen) {
        Screen.Home -> R.drawable.ic_home_icon
        Screen.Planner -> R.drawable.ic_calendar
        Screen.Profile -> R.drawable.ic_profile
        Screen.Saved -> R.drawable.ic_bookmark
    }
}