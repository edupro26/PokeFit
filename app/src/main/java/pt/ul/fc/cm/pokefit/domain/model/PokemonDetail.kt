package pt.ul.fc.cm.pokefit.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<String>
)