package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.PokemonListState
import pt.ul.fc.cm.pokefit.presentation.screens.pokemon.PokemonViewModel

@Composable
fun SelectStarterButton(
    selected: Int,
    state: PokemonListState,
    viewModel: PokemonViewModel
) {
    Button(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(),
        onClick = {
            if (selected != -1) {
                viewModel.chooseStarterPokemon(state.pokemon[selected])
            }
        },
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(
                    MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Select",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}