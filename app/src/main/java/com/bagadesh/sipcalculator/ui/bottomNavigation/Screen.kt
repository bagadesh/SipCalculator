package com.bagadesh.sipcalculator.ui.bottomNavigation

/**
 * Created by bagadesh on 31/07/22.
 */
sealed class Screen(val route: String, val name: String) {
    object Home : Screen("home", "Home")
    object Planner : Screen("planner", "Planner")
    object Profile : Screen("profile", "Profile")
    object Saved : Screen("saved", "Saved")
}
