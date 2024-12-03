package com.example.pokefit.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokefit.domain.Pokemon
import com.example.pokefit.services.pokeapi.PokeApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonViewModel : ViewModel() {
    private val apiService = PokeApi.create()
    private val _pokemonList = MutableStateFlow<List<Pokemon>>(emptyList())
    val pokemonList: StateFlow<List<Pokemon>> = _pokemonList

    init {
        fetchPokemon()
    }

    private fun fetchPokemon() {
        viewModelScope.launch {
            try {
                val size = 9 // TODO change this value in the future
                val list = apiService.getPokemonList(size)
                val pokemonList = list.results.map { item ->
                    val pokemon = apiService.getPokemonInfo(item.name)
                    Pokemon(
                        pokemon.id,
                        pokemon.name,
                        pokemon.types.map { type ->
                            type.type.name
                        }
                    )
                }
                _pokemonList.value = pokemonList
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
