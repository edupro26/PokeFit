package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail.components

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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.model.pokemon.PokemonStats
import pt.ul.fc.cm.pokefit.presentation.ui.theme.HappyFaceTint
import pt.ul.fc.cm.pokefit.presentation.ui.theme.NeutralFaceTint
import pt.ul.fc.cm.pokefit.presentation.ui.theme.SadFaceTint
import pt.ul.fc.cm.pokefit.utils.Constants.STAT_MAX

@Composable
fun StatsSection(
    level: Int,
    stats: PokemonStats,
    isLocked: Boolean
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
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 32.dp)
        ) {
            when {
                isLocked -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    ) {
                        LockedIcon()
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = stringResource(R.string.this_pokemon_is_locked),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
                else -> {
                    /* TODO progression logic */
                    ShowLevel(level)
                    Spacer(modifier = Modifier.height(32.dp))
                    ShowStats(stats)
                }
            }
        }
    }
}

@Composable
private fun ShowLevel(
    level: Int,
    curExp: Int = 40,
    maxExp: Int = 100,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Level",
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = level.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold,
            )
        }
        Box {
            LinearProgressIndicator(
                progress = { curExp.toFloat() / maxExp },
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primary
                    .copy(alpha = 0.2f),
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = "$curExp / $maxExp",
                modifier = Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun ShowStats(stats: PokemonStats) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        StatItem(
            value = stats.happiness,
            name = "Happiness",
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 1.dp,
            color = Color.Gray
        )
        StatItem(
            value = stats.physique,
            name = "Physique",
            modifier = Modifier.weight(1f)
        )
        VerticalDivider(
            modifier = Modifier.fillMaxHeight(),
            thickness = 1.dp,
            color = Color.Gray
        )
        StatItem(
            value = stats.health,
            name = "Health",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatItem(
    value: Int,
    name: String,
    modifier: Modifier
) {
    val progress = value.toFloat() / STAT_MAX
    val color = when {
        progress < 0.3 -> SadFaceTint
        progress < 0.7 -> NeutralFaceTint
        else -> HappyFaceTint
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.height(16.dp))
        LinearProgressIndicator(
            progress = { progress },
            color = color,
            trackColor = color.copy(alpha = 0.2f),
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )
    }
}

@Composable
private fun LockedIcon() {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.size(64.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_pokemon_locked),
                contentDescription = "Locked Pokemon",
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}