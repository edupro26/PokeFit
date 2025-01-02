package pt.ul.fc.cm.pokefit.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pt.ul.fc.cm.pokefit.R

@Composable
fun SelectedPokemon() {
    Card(
        onClick = { /* TODO */ },
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 3.dp
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .height(160.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                PokemonInfo()
            }
            Spacer(modifier = Modifier.size(16.dp))
            /*TODO show the selected pokemon*/
            val img = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/004.png"
            Image(
                painter = rememberAsyncImagePainter(model = img),
                contentDescription = "Selected Pokemon",
                modifier = Modifier.fillMaxSize(),
            )
        }
        PokemonExpProgress()
    }
}

@Composable
private fun PokemonInfo() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Charmander",
            style = MaterialTheme.typography.titleMedium
        )
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_happy_face),
                contentDescription = "Happiness",
                tint = Color(0xFF43A047)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Happiness",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_neutral_face),
                contentDescription = "Physique",
                tint = Color(0xFFFFB300)
            )
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Physique",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun PokemonExpProgress() {
    Box {
        Box(
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            LinearProgressIndicator(
                progress = { 0.4f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.secondary,
            )
            Text(
                text = "40 / 100",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 8.dp)
        ) {
            Surface(
                modifier = Modifier.border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = CircleShape
                    ),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.surfaceContainer,
            ) {
                Text(
                    text = "10",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp),
                )
            }
        }
    }
}
