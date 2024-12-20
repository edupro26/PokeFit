package pt.ul.fc.cm.pokefit.ui.screens.pokemon

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
import androidx.navigation.NavController
import androidx.hilt.navigation.compose.hiltViewModel
import pt.ul.fc.cm.pokefit.ui.screens.pokemon.components.PokemonCard
import pt.ul.fc.cm.pokefit.ui.screens.pokemon.components.ScreenTopBar
import pt.ul.fc.cm.pokefit.ui.common.BottomAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonScreen(
    navController: NavController,
    viewModel: PokemonViewModel = hiltViewModel()
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
                // Display Pokémon in a scrollable list
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(state.pokemon) { pokemon ->
                        PokemonCard(pokemon)
                    }
                }
            }
        }
    }
}