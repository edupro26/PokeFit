package com.example.pokefit.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.pokefit.api.domain.Pokemon
import com.example.pokefit.ui.viewModel.PokemonViewModel

@Composable
fun PokemonScreen(viewModel: PokemonViewModel = viewModel()) {
    // Observe Pokémon data from the ViewModel
    val pokemons = viewModel.pokemons.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Pokémon Screen",
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(16.dp)
        )

        if (pokemons.value.isNullOrEmpty()) {
            // Show a loading state while data is being fetched
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
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pokemons.value.filterNotNull().take(2)) { pokemon ->
                    PokemonCard(pokemon)
                }
            }
        }
    }
}

@Composable
fun PokemonCard(pokemon: Pokemon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Pokémon Image
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imageUrl),
                contentDescription = "${pokemon.name} image",
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            // Pokémon Details
            Column {
                Text(
                    text = pokemon.formattedName,
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = Color.Black
                )
                Text(
                    text = "Types: ${pokemon.types.joinToString(", ") { it.name.capitalize() }}",
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    color = Color.Gray
                )
            }
        }
    }
}
