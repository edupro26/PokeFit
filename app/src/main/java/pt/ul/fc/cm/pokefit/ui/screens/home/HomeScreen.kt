package pt.ul.fc.cm.pokefit.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.ui.screens.home.components.ScreenTopBar
import pt.ul.fc.cm.pokefit.ui.screens.home.components.StatsSection
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
        topBar = { ScreenTopBar(scrollBehavior) }
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
            )
            Spacer(modifier = Modifier.height(24.dp))
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
    caloriesProgress: Float = 0.5f,
    caloriesBurned: Int = 1000,
    calorieGoal: Int = 2000,
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
                .padding(8.dp)
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(PrimaryGrey)
                .padding(14.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // Centraliza os elementos horizontalmente
            )  {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize)) { // Tamanho menor para o texto
                            append("Burned ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize, // Texto do valor um pouco maior que o restante
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append("$caloriesBurned")
                        }
                        withStyle(style = SpanStyle(fontSize = MaterialTheme.typography.bodySmall.fontSize)) { // Tamanho menor para o texto
                            append(" out of $calorieGoal cal")
                        }
                    },
                    color = Color.Black
                )
                LinearProgressIndicator(
                    progress = { caloriesProgress },
                    modifier = Modifier
                        .weight(1f)
                        .height(4.dp),
                    color = Color.Red,
                    trackColor = Color(0xFFE0E0E0), // Light gray background
                )
            }
        }
    }
}