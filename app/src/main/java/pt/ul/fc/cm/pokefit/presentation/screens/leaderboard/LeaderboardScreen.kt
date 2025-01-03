package pt.ul.fc.cm.pokefit.presentation.screens.leaderboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.presentation.common.BottomAppBar
import pt.ul.fc.cm.pokefit.presentation.common.TopAppBar
import pt.ul.fc.cm.pokefit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    navController: NavController,
    navigate: (String, Boolean) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = { LeaderboardTopBar(scrollBehavior) },
        bottomBar = { BottomAppBar(navController, navigate) }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Leaderboard Screen",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun LeaderboardTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        firstIcon = R.drawable.ic_top_filters,
        firstDescription = "Filters",
        onFirstIconClick = { /*TODO*/ }
    )
}