package pt.ul.fc.cm.pokefit.data.remote.dtos

import com.google.gson.annotations.SerializedName
import pt.ul.fc.cm.pokefit.domain.model.PokemonDetail

data class PokemonDetailDto(
    val height: Int,
    val id: Int,
    @SerializedName("is_default")
    val name: String,
    val types: List<Type>,
    val weight: Int
)

fun PokemonDetailDto.fromDto(): PokemonDetail {
    return PokemonDetail(
        id = id,
        name = name,
        height = height,
        weight = weight,
        types = types.map { it.type.name }
    )
}