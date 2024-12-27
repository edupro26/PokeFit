package pt.ul.fc.cm.pokefit.ui.common

import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.ui.navigation.Screen

sealed class BottomBarItem(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Home : BottomBarItem(
        route = Screen.Home.route,
        title = "Home",
        icon = R.drawable.ic_bottom_home
    )
    object Pokemon : BottomBarItem(
        route = Screen.Pokemon.route,
        title = "Pokemon",
        icon = R.drawable.ic_bottom_pokemon
    )
    object Leaderboards : BottomBarItem(
        route = Screen.Leaderboards.route,
        title = "Leaderboards",
        icon = R.drawable.ic_bottom_leaderboards
    )
    object Profile : BottomBarItem(
        route = Screen.Profile.route,
        title = "Profile",
        icon = R.drawable.ic_bottom_profile
    )
}

