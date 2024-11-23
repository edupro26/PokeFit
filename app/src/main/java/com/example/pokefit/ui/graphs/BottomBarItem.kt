package com.example.pokefit.ui.graphs

import com.example.pokefit.R

sealed class BottomBarItem(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home : BottomBarItem(
        route = "home",
        title = "Home",
        icon = R.drawable.ic_home_icon
    )
    object Pokemon : BottomBarItem(
        route = "pokemon",
        title = "Pokemon",
        icon = R.drawable.ic_grid_icon
    )
    object Leaderboards : BottomBarItem(
        route = "leaderboards",
        title = "Leaderboards",
        icon = R.drawable.ic_leaderboard_icon
    )
    object Profile : BottomBarItem(
        route = "profile",
        title = "Profile",
        icon = R.drawable.ic_person_icon
    )
}

