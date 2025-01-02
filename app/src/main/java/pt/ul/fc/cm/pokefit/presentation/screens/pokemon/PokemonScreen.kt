package pt.ul.fc.cm.pokefit.presentation.screens.pokemon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.components.PokemonCard
import pt.ul.fc.cm.pokefit.presentation.common.BottomAppBar
import pt.ul.fc.cm.pokefit.presentation.common.TopAppBar
import pt.ul.fc.cm.pokefit.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonScreen(
    navController: NavController,
    navigate: (String, Boolean) -> Unit,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                firstIcon = R.drawable.ic_top_search,
                firstDescription = "Search",
                onFirstIconClick = { /*TODO*/ },
                secondIcon = R.drawable.ic_top_filters,
                secondDescription = "Filters",
                onSecondIconClick = { /*TODO*/ }
            )
        },
        bottomBar = { BottomAppBar(navController, navigate) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.isLoading) {
                // TODO: Display a loading indicator
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(start = 18.dp, end = 18.dp)
                ) {
                    items(state.pokemon) { pokemon ->
                        PokemonCard(pokemon)
                    }
                }
            }
        }
    }
}