package pt.ul.fc.cm.pokefit.ui.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Signin : Screen("signin")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object Pokemon : Screen("pokemon")
    object Leaderboards : Screen("leaderboards")
    object Profile : Screen("profile")
}