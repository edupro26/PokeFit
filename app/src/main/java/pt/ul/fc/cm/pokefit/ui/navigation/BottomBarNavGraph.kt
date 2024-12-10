package pt.ul.fc.cm.pokefit.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pt.ul.fc.cm.pokefit.ui.screens.home.HomeScreen
import pt.ul.fc.cm.pokefit.ui.screens.leaderboard.LeaderboardScreen
import pt.ul.fc.cm.pokefit.ui.screens.pokemon.PokemonScreen
import pt.ul.fc.cm.pokefit.ui.screens.profile.ProfileScreen

@Composable
fun BottomBarNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
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