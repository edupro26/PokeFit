package pt.ul.fc.cm.pokefit.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.ui.common.BottomAppBar
import pt.ul.fc.cm.pokefit.ui.navigation.Screen
import pt.ul.fc.cm.pokefit.ui.common.TopAppBar
import pt.ul.fc.cm.pokefit.ui.screens.profile.components.DisplayName
import pt.ul.fc.cm.pokefit.ui.screens.profile.components.ProfilePicture

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val isUserSignedIn = viewModel.isUserSignedIn.value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    ObserveSignoutEvent(isUserSignedIn, navController)
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                firstIcon = Icons.Default.Notifications,
                firstDescription = "Notifications",
                onFirstIconClick = { /*TODO*/ },
                secondIcon = Icons.Default.Settings,
                secondDescription = "Settings",
                onSecondIconClick = { /*TODO*/ }
            )
        },
        bottomBar = { BottomAppBar(navController) }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.user != null) {
                ProfilePicture(user = state.user)
                Spacer(modifier = Modifier.size(16.dp))
                DisplayName(
                    displayName = state.user.displayName,
                    username = state.user.username,
                    onSignout = { viewModel.signOut() }
                )
            }
            if (state.error.isNotBlank()) {
                Text(
                    text = "Error: ${state.error}",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                )
            }
            if (state.isLoading) CircularProgressIndicator()
        }
    }
}

@Composable
private fun ObserveSignoutEvent(
    isUserSignedIn: Boolean,
    navController: NavController
) {
    LaunchedEffect(isUserSignedIn) {
        if (!isUserSignedIn) {
            navController.navigate(Screen.Signin.route) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
                navController.graph.setStartDestination(Screen.Signin.route)
            }
        }
    }
}