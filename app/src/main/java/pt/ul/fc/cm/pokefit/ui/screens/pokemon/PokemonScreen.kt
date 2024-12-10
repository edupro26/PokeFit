package pt.ul.fc.cm.pokefit.ui.screens.pokemon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.ul.fc.cm.pokefit.ui.screens.pokemon.components.PokemonCard
import pt.ul.fc.cm.pokefit.ui.screens.pokemon.components.ScreenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonScreen() {
    val viewModel: PokemonViewModel = viewModel()
    val pokemonList by viewModel.pokemonList.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { ScreenTopBar(scrollBehavior) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (pokemonList.isEmpty()) {
                // Show a loading state while data is being fetched
                // TODO: improve this logic
                Text(
                    text = "Loading Pokémon...",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = Color.Gray,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                // Display Pokémon in a scrollable list
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(pokemonList) { pokemon ->
                        PokemonCard(pokemon)
                    }
                }
            }
        }
    }
}