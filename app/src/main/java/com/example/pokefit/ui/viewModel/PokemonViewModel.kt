package com.example.pokefit.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokefit.api.pokeApi.PokemonApi
import com.example.pokefit.api.domain.Pokemon

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    var pokemons = MutableLiveData<List<Pokemon?>>()

    init {
        loadPokemons()
    }

    private fun loadPokemons() {
        viewModelScope.launch(Dispatchers.IO) {
            val pokemonsApiResult = PokemonApi.listPokemons()

            pokemonsApiResult?.results?.let {
                val pokemonList = it.map { pokemonResult ->
                    val number = pokemonResult.url
                        .replace("https://pokeapi.co/api/v2/pokemon/", "")
                        .replace("/", "").toInt()

                    val pokemonApiResult = PokemonApi.getPokemon(number)

                    pokemonApiResult?.let {
                        Pokemon(
                            pokemonApiResult.id,
                            pokemonApiResult.name,
                            pokemonApiResult.types.map { type ->
                                type.type
                            }
                        )
                    }
                }
                pokemons.postValue(pokemonList)
            }
        }
    }
}
