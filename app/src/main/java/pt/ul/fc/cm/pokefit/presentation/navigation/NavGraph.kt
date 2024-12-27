package pt.ul.fc.cm.pokefit.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pt.ul.fc.cm.pokefit.presentation.screens.auth.signin.SigninScreen
import pt.ul.fc.cm.pokefit.presentation.screens.auth.signup.SignupScreen
import pt.ul.fc.cm.pokefit.presentation.screens.auth.splash.SplashScreen
import pt.ul.fc.cm.pokefit.presentation.screens.home.HomeScreen
import pt.ul.fc.cm.pokefit.presentation.screens.leaderboard.LeaderboardScreen
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.PokemonScreen
import pt.ul.fc.cm.pokefit.presentation.screens.profile.ProfileScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screen.Signin.route) {
            SigninScreen(navController)
        }
        composable(route = Screen.Signup.route) {
            SignupScreen(navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screen.Pokemon.route) {
            PokemonScreen(navController)
        }
        composable(route = Screen.Leaderboards.route) {
            LeaderboardScreen(navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController)
        }
    }
}