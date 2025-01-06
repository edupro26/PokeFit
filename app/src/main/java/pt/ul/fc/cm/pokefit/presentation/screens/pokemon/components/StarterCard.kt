package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.PokemonListState

@Composable
fun StarterCard(
    pokemonIndex: Int,
    selectedIndex: Int,
    state: PokemonListState,
    onSelect: () -> Unit
) {
    val pokemon = state.pokemon[pokemonIndex]
    Card(
        onClick = { onSelect() },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 3.dp
        ),
        border = if (pokemonIndex == selectedIndex)
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            ) else null,
        modifier = Modifier
            .height(130.dp)
            .fillMaxWidth()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberAsyncImagePainter(pokemon.imgUrl),
            contentDescription = "${pokemon.name} image"
        )
    }
}