package pt.ul.fc.cm.pokefit.domain.model

data class User(
    val uid: String = "",
    val email: String = "",
    val username: String = "",
    val displayName: String = "",
    val photoUrl: String? = null,
    val pokemonCount: Int = 0,
    val userScore: Int = 0,
    val fitCoins: Int = 500
)
