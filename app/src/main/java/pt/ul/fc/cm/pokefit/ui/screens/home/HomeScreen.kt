package pt.ul.fc.cm.pokefit.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.ui.theme.Pink80
import pt.ul.fc.cm.pokefit.ui.theme.Primary
import pt.ul.fc.cm.pokefit.ui.theme.PrimaryGrey
import pt.ul.fc.cm.pokefit.ui.theme.Transparent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val steps by viewModel.steps // Observe steps state
    val stepGoal = 10000 // Set a daily step goal
    val progress = (steps.toFloat() / stepGoal).coerceIn(0f, 1f)

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            modifier = Modifier.size(38.dp),
                            painter = painterResource(id = R.drawable.ic_app_logo),
                            contentDescription = "App Logo"
                        )
                        Text(
                            text = buildAnnotatedString {
                                withStyle(style = SpanStyle(color = Color.Black)) {
                                    append("POKÉ")
                                }
                                withStyle(style = SpanStyle(color = Primary)) {
                                    append("FIT")
                                }
                            },
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Goals"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pokemon Stats Section
            PokemonStatsSection(
                pokemonImage = painterResource(id = R.drawable.trainer),
                steps = steps,
                progress = progress
            )

            Spacer(modifier = Modifier.height(24.dp))

//            // General Stats
//            Text(
//                text = "Steps Taken: $steps",
//                style = MaterialTheme.typography.headlineMedium,
//                color = Color.Black
//            )
//            Spacer(modifier = Modifier.height(16.dp))
//            LinearProgressIndicator(
//                progress = { progress },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(8.dp)
//                    .padding(horizontal = 16.dp),
//                color = Primary,
//            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(
//                text = "Goal: $stepGoal steps",
//                style = MaterialTheme.typography.labelLarge,
//                color = Color.Gray
//            )
        }
    }
}

@Composable
fun PokemonStatsSection(
    pokemonName: String = "Pokemon",
    pokemonImage: Painter,
    level: Int = 14,
    xpProgress: Float = 0.75f,
    steps: Int = 0,
    heartRate: Int = 88,
    sleepDuration: String = "7h 34m",
    progress: Float = 0.56f,
    caloriesBurned: Float = 1116.5f,
    calorieGoal: Float = 2000f
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Pokémon Image and Name
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = pokemonImage,
                contentDescription = "$pokemonName Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .aspectRatio(1.5f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Transparent),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = pokemonName,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Level and XP Progress Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(PrimaryGrey)
                .padding(8.dp) // Padding inside the box
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LinearProgressIndicator(
                    progress = { xpProgress },
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp),
                    color = Color(0xFF4285F4), // Blue color for progress
                    trackColor = Color(0xFFE0E0E0), // Light gray background
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = MaterialTheme.typography.bodyLarge.fontSize)) {
                            append("Level ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("$level")
                        }
                    },
                    color = Color.Black
                )
            }
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Stats Section
        StatsSection(
            steps = steps.toString(),
            heartRate = "$heartRate bpm",
            sleepDuration = sleepDuration
        )

        // Progress Section
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "In-Progress",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Burned ${"%.1f".format(caloriesBurned)} out of ${calorieGoal.toInt()} cal",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun StatsSection(steps: String, heartRate: String, sleepDuration: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Column containing Steps and Sleep
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatItem(
                label = "Steps",
                value = steps,
                indicatorColor = Color(0xFF4CAF50) // Green
            )
            StatItem(
                label = "Sleep",
                value = sleepDuration,
                indicatorColor = Color(0xFFE91E63) // Pink
            )
        }

        //Heart Rate card
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(12.dp)) // Optional: Rounded corners
                .background(MaterialTheme.colorScheme.surface) // Optional: Add background color
                .padding(8.dp) // Padding inside the box
        ) {
            StatItem(
                label = "Heart Rate",
                value = heartRate,
                indicatorColor = Color(0xFFFF9800) // Orange
            )
        }
    }
}

@Composable
fun StatItem(label: String, value: String, indicatorColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Label
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium
            )
            // Value
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            // Indicator
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
                    .background(indicatorColor)
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen()
}