package pt.ul.fc.cm.pokefit.presentation.screens.initial

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen

@Composable
fun InitialScreen(
    navigate: (String, Boolean) -> Unit,
    viewModel: InitialViewModel = hiltViewModel(),
) {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val authState = viewModel.authState.value
        if (authState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (!authState.isSignedIn) {
            /**
             * TODO small app description and greetings
             * TODO continue to signin ou signout
             */
            navigate(Screen.Signin.route, true)
        } else {
            navigate(Screen.Home.route, true)
        }
    }
}