package pt.ul.fc.cm.pokefit.presentation.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import pt.ul.fc.cm.pokefit.presentation.common.BottomAppBar
import pt.ul.fc.cm.pokefit.presentation.common.TopAppBar
import pt.ul.fc.cm.pokefit.presentation.screens.profile.components.DisplayName
import pt.ul.fc.cm.pokefit.presentation.screens.profile.components.ProfilePicture
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    navigate: (String, Boolean) -> Unit,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val isUserSignedIn = viewModel.isUserSignedIn.value
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    ObserveSignoutEvent(isUserSignedIn, navigate)
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                firstIcon = R.drawable.ic_top_notifications,
                firstDescription = "Notifications",
                onFirstIconClick = { /*TODO*/ },
                secondIcon = R.drawable.ic_top_settings,
                secondDescription = "Settings",
                onSecondIconClick = { /*TODO*/ }
            )
        },
        bottomBar = { BottomAppBar(navController, navigate) }
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
    navigate: (String, Boolean) -> Unit
) {
    LaunchedEffect(isUserSignedIn) {
        if (!isUserSignedIn) {
            navigate(Screen.Signin.route, true)
        }
    }
}