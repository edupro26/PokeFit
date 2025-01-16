package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.presentation.common.BottomAppBar
import pt.ul.fc.cm.pokefit.presentation.common.TopAppBar
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.list.components.ConfirmationDialog
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.list.components.PokemonCard
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.list.components.SelectStarterButton
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.list.components.StarterCard

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
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = { PokemonTopBar(scrollBehavior) },
        bottomBar = { BottomAppBar(navController, navigate) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.pokemon.size > 3 -> {
                    LazyColumn(
                        modifier = Modifier
                            .padding(start = 18.dp, end = 18.dp)
                    ) {
                        items(state.pokemon) { pokemon ->
                            PokemonCard(pokemon)
                        }
                    }
                }
                state.pokemon.size == 3 -> {
                    ShowStarterPokemon(state, viewModel)
                }
                !state.error.isNullOrBlank() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${state.error}",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowStarterPokemon(
    state: PokemonListState,
    viewModel: PokemonViewModel
) {
    Column(
        modifier = Modifier
            .padding(start = 32.dp, end = 32.dp)
    ) {
        var selected by remember { mutableIntStateOf(-1) }
        var showConfirmation by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = stringResource(R.string.choose_your_starter_pokemon),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.size(24.dp))
        StarterCard(0, selected, state) {
            selected = if (selected != 0) 0 else -1
        }
        Spacer(modifier = Modifier.size(16.dp))
        StarterCard(1, selected, state) {
            selected = if (selected != 1) 1 else -1
        }
        Spacer(modifier = Modifier.size(16.dp))
        StarterCard(2, selected, state) {
            selected = if (selected != 2) 2 else -1
        }
        Spacer(modifier = Modifier.size(24.dp))
        SelectStarterButton {
            if (selected != -1) showConfirmation = true
        }
        if (showConfirmation) {
            ConfirmationDialog(
                pokemon = state.pokemon[selected],
                onConfirm = { pokemon ->
                    viewModel.chooseStarterPokemon(pokemon)
                    showConfirmation = false
                },
                onCancel = { showConfirmation = false }
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun PokemonTopBar(scrollBehavior: TopAppBarScrollBehavior) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        firstIcon = R.drawable.ic_top_search,
        firstDescription = "Search",
        onFirstIconClick = { /*TODO*/ },
        secondIcon = R.drawable.ic_top_filters,
        secondDescription = "Filters",
        onSecondIconClick = { /*TODO*/ }
    )
}