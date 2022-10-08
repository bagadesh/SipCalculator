package com.bagadesh.sipcalculator.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bagadesh.sipcalculator.BuildConfig
import com.bagadesh.sipcalculator.R
import com.bagadesh.sipcalculator.home.ui.HomeUI
import com.bagadesh.sipcalculator.planner.PlannerUI
import com.bagadesh.sipcalculator.planner.PlannerViewModel
import com.bagadesh.sipcalculator.savedResults.ui.SavedResultsUI
import com.bagadesh.sipcalculator.ui.bottomNavigation.Screen
import com.bagadesh.sipcalculator.ui.profile.ProfileUI
import com.bagadesh.sipcalculator.ui.splash.SplashScreenController
import com.bagadesh.sipcalculator.ui.theme.MaterialColorUI

/**
 * Created by bagadesh on 31/07/22.
 */

@Composable
fun MainUI() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavigationUI(navController = navController)
        },
        topBar = {
            if (BuildConfig.DEBUG) {
                MaterialColorUI()
            }
            Spacer(modifier = Modifier.size(10.dp))
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding),
            startDestination = Screen.Home.route
        ) {
            composable(route = Screen.Home.route) {
                SplashScreenController.homeUIDrawn()
                HomeUI()
            }
            composable(route = Screen.Planner.route) {
                SplashScreenController.homeUIDrawn()
                val viewModel = hiltViewModel<PlannerViewModel>()
                PlannerUI(viewModel)
            }
            composable(route = Screen.Profile.route) {
                SplashScreenController.homeUIDrawn()
                ProfileUI()
            }
            composable(route = Screen.Saved.route) {
                SplashScreenController.homeUIDrawn()
                SavedResultsUI()
            }
        }
    }
}

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