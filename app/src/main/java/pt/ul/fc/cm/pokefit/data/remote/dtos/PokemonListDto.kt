package pt.ul.fc.cm.pokefit.data.remote.dtos

import com.google.gson.annotations.SerializedName
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon

data class PokemonListDto(
    val count: Int,
    val next: String,
    val previous: Any,
    @SerializedName("results")
    val pokemon: List<PokemonEntryDto>
)

fun PokemonListDto.fromDto(): List<Pokemon> {
    return pokemon.map { it.fromDto() }
}