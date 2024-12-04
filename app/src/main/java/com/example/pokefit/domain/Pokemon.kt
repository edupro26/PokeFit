package com.example.pokefit.domain

data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<String>
) {
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}
