package com.example.pokefit.ui.graphs

import com.example.pokefit.ui.screens.home.HomeScreen
import com.example.pokefit.ui.screens.pokemon.PokemonScreen
import com.example.pokefit.ui.screens.leaderboard.LeaderboardScreen
import com.example.pokefit.ui.screens.profile.ProfileScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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