package pt.ul.fc.cm.pokefit.ui.screens.pokemon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pt.ul.fc.cm.pokefit.domain.model.Pokemon

@Composable
fun PokemonCard(pokemon: Pokemon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .size(100.dp)
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
                painter = rememberAsyncImagePainter(model = pokemon.imgUrl),
                contentDescription = "${pokemon.name} image",
                modifier = Modifier.size(94.dp),
                contentScale = ContentScale.Crop
            )
            // Pokémon Details
            Column {
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    color = Color.Black
                )
            }
            /* TODO: Add a pokemon level indicator */
        }
    }
}