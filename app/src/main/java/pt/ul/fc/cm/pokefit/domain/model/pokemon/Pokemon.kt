package pt.ul.fc.cm.pokefit.domain.model.pokemon

data class Pokemon(
    val id: Int? = null,
    val name: String? = null,
    val imgUrl: String? = null,
    val level: Int = 1,
    val details: PokemonDetails = PokemonDetails(),
    val stats: PokemonStats = PokemonStats(),
    val locked: Boolean = true,
    val selected: Boolean = false,
)