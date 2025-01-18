package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.model.pokemon.PokemonDetails
import kotlin.math.round

@Composable
fun DetailsSection(
    id: Int,
    name: String,
    imgUrl: String,
    details: PokemonDetails,
    imageSize: Dp = 160.dp
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxWidth()
    ) {
        DetailCard(id, name, imageSize, details)
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(imageSize + 8.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.outlineVariant,
                    CircleShape
                )
        ) {
            Image(
                painter = rememberAsyncImagePainter(imgUrl),
                contentDescription = "Pokemon image",
                modifier = Modifier
                    .size(imageSize)
                    .padding(8.dp)
            )
        }

    }
}

@Composable
private fun DetailCard(
    id: Int,
    name: String,
    imageSize: Dp,
    details: PokemonDetails
) {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 3.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = imageSize / 2f)
    ) {
        Spacer(modifier = Modifier.size(imageSize / 2f + 24.dp))
        PokemonTitle(id, name)
        Spacer(modifier = Modifier.size(24.dp))
        PokemonTypes(details.types!!)
        Spacer(modifier = Modifier.size(24.dp))
        PokemonDimensions(height = details.height!!, weight = details.weight!!)
        Spacer(modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun PokemonTitle(id: Int, name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "#$id",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = name.replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
private fun PokemonTypes(types: List<String>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    ) {
        types.forEach {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary)
            ) {
                Text(
                    text = it.replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(8.dp),
                )
            }
        }
    }
}

@Composable
private fun PokemonDimensions(
    height: Int,
    weight: Int
) {
    val weightInKg = remember { round(weight * 100f) / 1000f }
    val heightInMeters = remember { round(height * 100f) / 1000f }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        DimensionSection(
            value = weightInKg,
            unit = "kg",
            painter = painterResource(R.drawable.ic_pokemon_weight),
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 1.dp,
            color = Color.Gray
        )
        DimensionSection(
            value = heightInMeters,
            unit = "m",
            painter = painterResource(R.drawable.ic_pokemon_height),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun DimensionSection(
    value: Float,
    unit: String,
    painter: Painter,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Icon(
            painter = painter,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            contentDescription = "$unit icon"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "$value $unit",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}