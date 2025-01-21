package pt.ul.fc.cm.pokefit.presentation.screens.home.home

data class HomeState(
    val steps: Int = 0,
    val calories: Int = 0,
    val distance: Double = 0.0,
    val activeMinutes: Int = 0
)