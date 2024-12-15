package pt.ul.fc.cm.pokefit.ui.screens.auth.splash

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.ui.navigation.Screen

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashViewModel = hiltViewModel()
) {
    if (viewModel.isSignedIn()) {
        navController.navigate(Screen.Home.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            navController.graph.setStartDestination(Screen.Home.route)
        }
    } else {
        navController.navigate(Screen.Signin.route) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            navController.graph.setStartDestination(Screen.Signin.route)
        }
    }
}