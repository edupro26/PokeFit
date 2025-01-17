package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail

import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon

data class DetailState(
    val isLoading: Boolean = false,
    val pokemon: Pokemon? = null,
    val error: String? = null
)