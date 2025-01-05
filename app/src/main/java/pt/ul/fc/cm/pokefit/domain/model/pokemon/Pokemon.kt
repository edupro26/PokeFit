package pt.ul.fc.cm.pokefit.domain.model.pokemon

data class Pokemon(
    val id: Int,
    val name: String,
    val imgUrl: String,
    val level: Int = 1,
    val details: PokemonDetails = PokemonDetails(),
    val stats: PokemonStats = PokemonStats(),
    val isLocked: Boolean = true,
    val isSelected: Boolean = false,
)