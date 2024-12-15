package pt.ul.fc.cm.pokefit.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import pt.ul.fc.cm.pokefit.ui.common.BottomAppBar
import pt.ul.fc.cm.pokefit.ui.screens.profile.components.ScreenTopBar
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.model.User
import pt.ul.fc.cm.pokefit.ui.screens.profile.components.DisplayName
import pt.ul.fc.cm.pokefit.ui.screens.profile.components.ProfilePicture
import pt.ul.fc.cm.pokefit.ui.theme.PrimaryGrey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { ScreenTopBar(scrollBehavior) },
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
                    onSignout = { viewModel.signOut() }
                )
            }
            if (state.error.isNotBlank()) {
                Text(
                    text = "Error: ${state.error}",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            if (state.isLoading) CircularProgressIndicator()
        }
    }
}