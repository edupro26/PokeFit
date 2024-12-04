package pt.ul.fc.cm.pokefit.ui.graphs

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
        startDestination = BottomBarItem.Home.route
    ) {
        composable(route = BottomBarItem.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarItem.Pokemon.route) {
            PokemonScreen()
        }
        composable(route = BottomBarItem.Leaderboards.route) {
            LeaderboardScreen()
        }
        composable(route = BottomBarItem.Profile.route) {
            ProfileScreen()
        }
    }
}