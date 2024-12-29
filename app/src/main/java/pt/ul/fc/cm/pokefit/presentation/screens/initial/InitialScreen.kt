package pt.ul.fc.cm.pokefit.presentation.screens.initial

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen

@Composable
fun InitialScreen(
    navigate: (String, Boolean) -> Unit,
    viewModel: InitialViewModel = hiltViewModel(),
) {

    /**
     * TODO this will be the first screen
     * - small app description and greetings
     * - continue to signin screen
     * - only show if it's the first time opening the app
     */

    if (viewModel.isSignedIn()) {
        navigate(Screen.Home.route, true)
    } else {
        navigate(Screen.Signin.route, true)
    }
}