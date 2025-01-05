package pt.ul.fc.cm.pokefit.domain.model.pokemon

data class PokemonDetails(
    val rarity: Int? = null,
    val height: Int? = null,
    val weight: Int? = null,
    val types: List<String>? = null,
)