package pt.ul.fc.cm.pokefit.ui.screens.pokemon.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pt.ul.fc.cm.pokefit.domain.model.Pokemon

@Composable
fun PokemonCard(pokemon: Pokemon) {
    Surface (
        onClick = { /* TODO */ },
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
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
            Image(
                painter = rememberAsyncImagePainter(model = pokemon.imgUrl),
                contentDescription = "${pokemon.name} image",
                modifier = Modifier.size(84.dp),
                contentScale = ContentScale.Crop
            )
            Column {
                Text(
                    text = pokemon.name.replaceFirstChar { it.uppercase() },
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
            /* TODO: Add a pokemon level indicator */
        }
    }
}