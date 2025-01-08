package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon

@Composable
fun PokemonCard(pokemon: Pokemon) {
    Card(
        onClick = { /* TODO navigate to details screen */ },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 3.dp
        ),
        border = if (pokemon.selected) BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ) else null,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp, bottom = 16.dp,
                    start = 18.dp, end = 32.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PokemonImage(pokemon)
            Spacer(modifier = Modifier.size(24.dp))
            PokemonName(pokemon)
            Spacer(modifier = Modifier.weight(1f))
            PokemonLevel(pokemon)
        }
    }
}

@Composable
private fun PokemonImage(pokemon: Pokemon) {
    Image(
        painter = rememberAsyncImagePainter(model = pokemon.imgUrl),
        contentDescription = "${pokemon.name} image",
        modifier = Modifier.size(84.dp)
    )
}

@Composable
private fun PokemonName(pokemon: Pokemon) {
    Text(
        text = pokemon.name!!.replaceFirstChar { it.uppercase() },
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun PokemonLevel(pokemon: Pokemon) {
    Surface(
        shape = CircleShape,
        color = if (pokemon.locked) {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.primary
        },
        modifier = Modifier.size(44.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(4.dp)
        ) {
            if (pokemon.locked) {
                Icon(
                    painter = painterResource(R.drawable.ic_pokemon_locked),
                    contentDescription = "Locked Pokemon",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    text = pokemon.level.toString(),
                    style = if (pokemon.level <= 99) {
                        MaterialTheme.typography.bodyLarge
                    } else {
                        MaterialTheme.typography.bodyMedium
                    },
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }
}