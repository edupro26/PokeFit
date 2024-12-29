package pt.ul.fc.cm.pokefit.presentation.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import pt.ul.fc.cm.pokefit.R

@Composable
fun StatsSection(
    steps: String,
    distanceCovered: String,
    sleepDuration: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(188.dp)
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Coluna contendo Steps e Sleep
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatItem(
                label = stringResource(R.string.steps),
                value = steps,
                indicatorColor = Color(0xFF4CAF50) // Green
            )
            StatItem(
                label = stringResource(R.string.sleep),
                value = sleepDuration,
                indicatorColor = Color(0xFFE91E63) // Pink
            )
        }

        // Cartão de Heart Rate
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(12.dp)) // Cantos arredondados
                .background(MaterialTheme.colorScheme.surface) // Cor de fundo
        ) {
            StatItem(
                label = stringResource(R.string.distance),
                value = distanceCovered,
                indicatorColor = Color(0xFFFF9800), // Laranja
                size = 172.dp // Define altura para o StatItem
            )

        }
    }
}