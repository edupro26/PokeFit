package pt.ul.fc.cm.pokefit.data.remote.dtos

import com.google.gson.annotations.SerializedName
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.domain.model.pokemon.PokemonDetails
import pt.ul.fc.cm.pokefit.utils.Constants.POKE_IMG_FORMAT
import pt.ul.fc.cm.pokefit.utils.Constants.POKE_IMG_URL
import java.util.Locale

data class PokemonDto(
    val id: Int,
    val name: String,
    @SerializedName("base_experience")
    val rarity: Int,
    val weight: Int,
    val height: Int,
    val types: List<Type>,
)

fun PokemonDto.fromDto(): Pokemon {
    val imgId = String.format(Locale.ENGLISH, "%03d", id)
    return Pokemon(
        id = id,
        name = name,
        imgUrl = "$POKE_IMG_URL$imgId$POKE_IMG_FORMAT",
        details = PokemonDetails(
            rarity = rarity,
            weight = weight,
            height = height,
            types = types.map { it.type.name }
        )
    )
}