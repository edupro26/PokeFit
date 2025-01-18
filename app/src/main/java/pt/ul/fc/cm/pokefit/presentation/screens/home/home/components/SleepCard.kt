package pt.ul.fc.cm.pokefit.presentation.screens.home.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import pt.ul.fc.cm.pokefit.R

@Composable
fun SleepCard() {
    Card(
        onClick = { /* TODO */ },
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
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Sleep",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    SleepIcon()
                    Spacer(modifier = Modifier.size(12.dp))
                    SleepValue()
                }
            }
            Spacer(modifier = Modifier.width(42.dp))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "00:00 - 08:30",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.alpha(0.4f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                LinearProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier
                        .height(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.secondary
                        .copy(alpha = 0.6f),
                )
            }
        }
    }
}

@Composable
private fun SleepValue() {
    Row {
        Text(
            text = "8",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = "h ",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .alpha(0.4f)
                .align(Alignment.Bottom)
        )
        Text(
            text = "30",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Text(
            text = "m",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier
                .alpha(0.4f)
                .align(Alignment.Bottom)
        )
    }
}

@Composable
private fun SleepIcon() {
    Surface(
        shape = CircleShape,
        color = MaterialTheme.colorScheme.secondary,
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .padding(4.dp),
                painter = painterResource(R.drawable.ic_stats_sleep),
                contentDescription = "Sleep",
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }
    }
}