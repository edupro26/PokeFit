package pt.ul.fc.cm.pokefit.presentation.navigation

sealed class Screen(val route: String) {

    data object Initial : Screen("initial")

    data object Signin : Screen("signin")

    data object Signup : Screen("signup")

    data object Home : Screen("home")

    data object Pokemon : Screen("pokemon")

    data object Leaderboards : Screen("leaderboards")

    data object Profile : Screen("profile")

}