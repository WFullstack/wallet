package com.berakahnd.wallet.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.berakahnd.wallet.ui.viewmodel.HomeViewModel

@Composable
fun AppNavHost(
    viewModel: HomeViewModel =  hiltViewModel(),
    navController: NavHostController,
    startDestination: String = NavigationItem.Started.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Started.route) {
            StartedScreen(
                onStartedClick = {
                    navController.navigate(Screen.HOME.name)
                }
            )
        }
        composable(NavigationItem.Home.route) {
            HomeScreen(
                viewModel  = viewModel,
                onShowDashboardClick = {
                    navController.navigate(Screen.DASHBOARD.name)
                }
            )
        }
        composable(NavigationItem.Dashboard.route) {
            DashboardScreen(
                viewModel  = viewModel,
                onNavigateBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
enum class Screen {
    STARTED,
    HOME,
    DASHBOARD
}
sealed class NavigationItem(val route: String) {
    object Started : NavigationItem(Screen.STARTED.name)
    object Dashboard : NavigationItem(Screen.DASHBOARD.name)
    object Home : NavigationItem(Screen.HOME.name)
}
