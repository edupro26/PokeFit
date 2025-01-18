package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.common.ConfirmationDialog
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail.components.DetailButton
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail.components.DetailTopBar
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail.components.DetailsSection
import pt.ul.fc.cm.pokefit.utils.Constants.RARITY_1
import pt.ul.fc.cm.pokefit.utils.Constants.RARITY_2
import pt.ul.fc.cm.pokefit.utils.Constants.RARITY_3
import pt.ul.fc.cm.pokefit.R

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    Scaffold (
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = { DetailTopBar(state.pokemon, navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                state.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                state.pokemon != null -> {
                    ShowPokemonDetails(
                        pokemon = state.pokemon,
                        navController = navController,
                        viewModel = viewModel
                    )
                }
                !state.error.isNullOrBlank() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Error: ${state.error}",
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowPokemonDetails(
    pokemon: Pokemon,
    navController: NavController,
    viewModel: DetailViewModel
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .padding(start = 18.dp, end = 18.dp)
    ) {
        Column {
            when {
                !pokemon.locked -> {
                    UnLockedPokemon(
                        pokemon = pokemon,
                        context = context,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
                pokemon.locked -> {
                    LockedPokemon(
                        pokemon = pokemon,
                        context = context,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
private fun UnLockedPokemon(
    pokemon: Pokemon,
    context: Context,
    viewModel: DetailViewModel,
    navController: NavController,
) {
    DetailsSection(
        id = pokemon.id!!,
        name = pokemon.name!!,
        imgUrl = pokemon.imgUrl!!,
        details = pokemon.details
    )
    Spacer(modifier = Modifier.size(16.dp))
    DetailButton(
        text = stringResource(R.string.select),
        containerColor = MaterialTheme.colorScheme.primary,
        textColor = MaterialTheme.colorScheme.onPrimary
    ) {
        viewModel.selectPokemon(
            id = pokemon.id,
            context = context,
            popStack = {
                navController.navigate(Screen.PokemonList.route) {
                    popUpTo(Screen.PokemonList.route) { inclusive = true }
                }
            }
        )
    }
}

@Composable
private fun LockedPokemon(
    pokemon: Pokemon,
    context: Context,
    viewModel: DetailViewModel,
    navController: NavController
) {
    var showConfirmation by remember { mutableStateOf(false) }
    val fitCoins = calculateFitCoins(pokemon)
    DetailsSection(
        id = pokemon.id!!,
        name = pokemon.name!!,
        imgUrl = pokemon.imgUrl!!,
        details = pokemon.details
    )
    Spacer(modifier = Modifier.size(16.dp))
    DetailButton(
        text = fitCoins,
        containerColor = MaterialTheme.colorScheme.primary,
        textColor = MaterialTheme.colorScheme.onPrimary,
        painter = painterResource(R.drawable.ic_fit_coins),
        onClick = { showConfirmation = true }
    )
    if (showConfirmation) {
        ConfirmationDialog(
            text = stringResource(R.string.confirm_unlock),
            onCancel = { showConfirmation = false },
            onConfirm = {
                viewModel.unlockPokemon(
                    pokemon = pokemon,
                    amount = fitCoins.toInt(),
                    context = context,
                    popStack = {
                        navController.navigate(Screen.PokemonList.route) {
                            popUpTo(Screen.PokemonList.route) { inclusive = true }
                        }
                    }
                )
                showConfirmation = false
            }
        )
    }
}

private fun calculateFitCoins(pokemon: Pokemon): String {
    val rarity = pokemon.details.rarity!!
    return when {
        rarity < RARITY_1 -> "1000"
        rarity in RARITY_1..<RARITY_2 -> "3000"
        rarity in RARITY_2..<RARITY_3 -> "6000"
        else -> "9000"
    }
}
