package pt.ul.fc.cm.pokefit.data.remote.dtos

import pt.ul.fc.cm.pokefit.domain.model.Pokemon
import pt.ul.fc.cm.pokefit.utils.Constants.POKEMON_IMG_FORMAT
import pt.ul.fc.cm.pokefit.utils.Constants.POKEMON_IMG_URL

data class PokemonDto(
    val name: String,
    val url: String
)

fun PokemonDto.fromDto(): Pokemon {
    val id = url.split("/").filter { it.isNotEmpty() }.last().toInt()
    return Pokemon(
        name = name,
        imgUrl = "$POKEMON_IMG_URL$id$POKEMON_IMG_FORMAT"
    )
}