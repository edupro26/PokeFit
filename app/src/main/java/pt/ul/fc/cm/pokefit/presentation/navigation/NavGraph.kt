package pt.ul.fc.cm.pokefit.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pt.ul.fc.cm.pokefit.presentation.screens.auth.signin.SigninScreen
import pt.ul.fc.cm.pokefit.presentation.screens.auth.signup.SignupScreen
import pt.ul.fc.cm.pokefit.presentation.screens.initial.InitialScreen
import pt.ul.fc.cm.pokefit.presentation.screens.home.home.HomeScreen
import pt.ul.fc.cm.pokefit.presentation.screens.home.map.MapScreen
import pt.ul.fc.cm.pokefit.presentation.screens.leaderboard.LeaderboardScreen
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail.DetailScreen
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.list.ListScreen
import pt.ul.fc.cm.pokefit.presentation.screens.profile.ProfileScreen
import pt.ul.fc.cm.pokefit.utils.Constants.PARAM_POKEMON_ID

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Initial.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(route = Screen.Initial.route) {
            InitialScreen(navigate = navController::navigate)
        }
        composable(route = Screen.Signin.route) {
            SigninScreen(navigate = navController::navigate)
        }
        composable(route = Screen.Signup.route) {
            SignupScreen(navigate = navController::navigate)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                navController = navController,
                navigate = navController::navigate,
            )
        }
        composable(route = Screen.Map.route) {
            MapScreen(navController = navController)
        }
        composable(route = Screen.PokemonList.route) {
            ListScreen(
                navController = navController,
                navigate = navController::navigate
            )
        }
        composable(
            route = Screen.PokemonDetail.route + "/{$PARAM_POKEMON_ID}"
        ) {
            DetailScreen(navController = navController)
        }
        composable(route = Screen.Leaderboards.route) {
            LeaderboardScreen(
                navController = navController,
                navigate = navController::navigate
            )
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(
                navController = navController,
                navigate = navController::navigate
            )
        }
    }
}

fun NavHostController.navigate(route: String, reset: Boolean) = navigate(route) {
    popUpTo(graph.startDestinationId) {
        inclusive = reset
    }
    if (reset) {
        graph.setStartDestination(route)
    }
    launchSingleTop = true
}