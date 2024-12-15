package pt.ul.fc.cm.pokefit.domain.model

data class User(
    val uid: String,
    val email: String,
    val displayName: String,
    val photoUrl: String? = null,
)
