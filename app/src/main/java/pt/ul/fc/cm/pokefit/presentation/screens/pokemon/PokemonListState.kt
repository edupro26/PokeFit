package pt.ul.fc.cm.pokefit.presentation.screens.pokemon

import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon

data class PokemonListState(
    val isLoading: Boolean = false,
    val isChoosing: Boolean = false,
    val pokemon: List<Pokemon> = emptyList(),
    val error: String? = null
)