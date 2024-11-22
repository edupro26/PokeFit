package com.example.pokefit.ui.graphs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarItem(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )
    object Pokemon : BottomBarItem(
        route = "pokemon",
        title = "Pokemon",
        icon = Icons.Default.Search // TODO find a better icon
    )
    object Leaderboards : BottomBarItem(
        route = "leaderboards",
        title = "Leaderboards",
        icon = Icons.Default.Menu // TODO find a better icon
    )
    object Profile : BottomBarItem(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person // TODO find a better icon
    )
}

