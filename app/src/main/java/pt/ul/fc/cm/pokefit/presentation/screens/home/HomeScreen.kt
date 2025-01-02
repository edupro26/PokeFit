package pt.ul.fc.cm.pokefit.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.presentation.common.BottomAppBar
import pt.ul.fc.cm.pokefit.presentation.common.TopAppBar
import pt.ul.fc.cm.pokefit.presentation.screens.home.components.PermissionHandler
import pt.ul.fc.cm.pokefit.presentation.screens.home.components.SelectedPokemon
import pt.ul.fc.cm.pokefit.presentation.screens.home.components.SleepCard
import pt.ul.fc.cm.pokefit.presentation.screens.home.components.StatsCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    navigate: (String, Boolean) -> Unit,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val steps by viewModel.steps
    val calories = 0 // TODO
    val distance = 0 // TODO
    val timeActive = 0 // TODO
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    PermissionHandler(
        context = LocalContext.current,
        countSteps = { viewModel.countSteps() }
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { HomeTopBar(scrollBehavior) },
        bottomBar = { BottomAppBar(navController, navigate) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 18.dp, end = 18.dp)
            ) {
                SelectedPokemon()
                Spacer(modifier = Modifier.size(24.dp))
                StatsSection(steps, calories, distance, timeActive)
                Spacer(modifier = Modifier.size(24.dp))
                SleepCard()
            }
        }
    }
}

@Composable
private fun StatsSection(
    steps: Int,
    calories: Int,
    distance: Int,
    timeActive: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatsCard(
            value = steps.toString(),
            goal = "6000",
            unit = "steps",
            title = "Steps",
            icon = R.drawable.ic_stats_steps,
            iconTint = Color(0xFF4CAF50),
            modifier = Modifier.weight(1f)
        )
        StatsCard(
            value = calories.toString(),
            goal = "500",
            unit = "kcal",
            title = "Calories",
            icon = R.drawable.ic_stats_calories,
            iconTint = Color(0xFFE91E63),
            modifier = Modifier.weight(1f)
        )
    }
    Spacer(modifier = Modifier.size(20.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatsCard(
            value = distance.toString(),
            goal = "5",
            unit = "km",
            title = "Distance",
            icon = R.drawable.ic_stats_distance,
            iconTint = Color(0xFFFF5722),
            modifier = Modifier.weight(1f)
        )
        StatsCard(
            value = timeActive.toString(),
            goal = "30",
            unit = "min",
            title = "Time Active",
            icon = R.drawable.ic_stats_time,
            iconTint = Color(0xFF2196F3),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun HomeTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        firstIcon = R.drawable.ic_top_map,
        firstDescription = "Map",
        onFirstIconClick = { /*TODO*/ },
        secondIcon = R.drawable.ic_top_tasks,
        secondDescription = "Goals",
        onSecondIconClick = { /*TODO*/ },
    )
}