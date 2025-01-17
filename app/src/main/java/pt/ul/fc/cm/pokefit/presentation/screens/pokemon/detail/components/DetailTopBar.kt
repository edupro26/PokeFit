package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import pt.ul.fc.cm.pokefit.R
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    pokemon: Pokemon?,
    navController: NavController
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceContainer
        ),
        navigationIcon = {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_back),
                    contentDescription = "Back Button"
                )
            }
        },
        title = {
            if (pokemon != null) {
                Text(
                    text = pokemon.name!!.replaceFirstChar { it.uppercase() },
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    )
}