package pt.ul.fc.cm.pokefit.presentation.navigation

sealed class Screen(val route: String) {

    data object Initial : Screen("initial")

    data object Signin : Screen("signin")

    data object Signup : Screen("signup")

    data object Home : Screen("home")

    data object Map : Screen("map")

    data object PokemonList : Screen("pokemon_list")

    data object PokemonDetail : Screen("pokemon_detail")

    data object Leaderboards : Screen("leaderboards")

    data object Profile : Screen("profile")

}