package pt.ul.fc.cm.pokefit.presentation.common

import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen

sealed class BottomBarItem(
    val route: String,
    val title: Int,
    val icon: Int
) {
    data object Home : BottomBarItem(
        route = Screen.Home.route,
        title = R.string.home,
        icon = R.drawable.ic_bottom_home
    )
    data object Pokemon : BottomBarItem(
        route = Screen.PokemonList.route,
        title = R.string.pokemon,
        icon = R.drawable.ic_bottom_pokemon
    )
    data object Leaderboards : BottomBarItem(
        route = Screen.Leaderboards.route,
        title = R.string.leaderboards,
        icon = R.drawable.ic_bottom_leaderboards
    )
    data object Profile : BottomBarItem(
        route = Screen.Profile.route,
        title = R.string.profile,
        icon = R.drawable.ic_bottom_profile
    )
}

