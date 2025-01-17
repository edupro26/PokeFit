package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.presentation.navigation.Screen
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail.components.DetailButton
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail.components.DetailTopBar

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
    Column(
        modifier = Modifier
            .padding(start = 18.dp, end = 18.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(pokemon.imgUrl),
            contentDescription = "${pokemon.name} image",
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
        )
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
                LockedPokemon()
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
    /* TODO show pokemon unlocked details */
    DetailButton(
        text = stringResource(R.string.select),
        containerColor = MaterialTheme.colorScheme.primary,
        textColor = MaterialTheme.colorScheme.onPrimary
    ) {
        viewModel.selectPokemon(
            id = pokemon.id!!,
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
private fun LockedPokemon() {
    /* TODO show pokemon locked details */
    DetailButton(
        text = "Unlock",
        containerColor = MaterialTheme.colorScheme.secondary,
        textColor = MaterialTheme.colorScheme.onSecondary
    ) {

    }
}
