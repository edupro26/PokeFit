package pt.ul.fc.cm.pokefit.data.remote.dtos

import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.utils.Constants.POKE_IMG_FORMAT
import pt.ul.fc.cm.pokefit.utils.Constants.POKE_IMG_URL
import java.util.Locale

data class PokemonEntryDto(
    val name: String,
    val url: String
)

fun PokemonEntryDto.fromDto(): Pokemon {
    val id = url.split("/").last { it.isNotEmpty() }.toInt()
    val imgId = String.format(Locale.ENGLISH, "%03d", id)
    return Pokemon(
        id = id,
        name = name,
        imgUrl = "$POKE_IMG_URL$imgId$POKE_IMG_FORMAT"
    )
}