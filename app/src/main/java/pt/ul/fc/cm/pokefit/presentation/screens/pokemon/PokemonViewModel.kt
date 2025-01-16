package pt.ul.fc.cm.pokefit.presentation.screens.pokemon

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.ul.fc.cm.pokefit.domain.usecase.PokemonList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.model.pokemon.Pokemon
import pt.ul.fc.cm.pokefit.domain.usecase.UserAccount
import pt.ul.fc.cm.pokefit.utils.Resource
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    userAccount: UserAccount,
    private val pokemonList: PokemonList
) : ViewModel() {

    private val _state = mutableStateOf(PokemonListState())
    val state: State<PokemonListState> = _state

    private val uid = userAccount.getCurrentUser()!!.uid

    init {
        viewModelScope.launch {
            val pokemonCount = getUserPokemonCount()
            when {
                pokemonCount > 0 -> loadPokemonList()
                pokemonCount == 0 -> loadStarterPokemon()
            }
        }
    }

    fun chooseStarterPokemon(pokemon: Pokemon) = viewModelScope.launch {
        _state.value = PokemonListState(isLoading = true)
        when (val response = pokemonList.saveStarterPokemon(uid, pokemon)) {
            is Response.Success -> loadPokemonList()
            is Response.Failure -> {
                _state.value = PokemonListState(error = response.error)
            }
        }
    }

    private fun loadPokemonList() {
        pokemonList.loadPokemonList(uid).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = PokemonListState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = PokemonListState(pokemon = resource.data)
                }
                is Resource.Failure -> {
                    _state.value = PokemonListState(error = resource.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadStarterPokemon() {
        pokemonList.loadStarterPokemon().onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = PokemonListState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = PokemonListState(pokemon = resource.data)
                }
                is Resource.Failure -> {
                    _state.value = PokemonListState(error = resource.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    private suspend fun getUserPokemonCount(): Int {
        _state.value = PokemonListState(isLoading = true)
        val response = pokemonList.getUserPokemonCount(uid)
        return when (response) {
            is Response.Success -> response.data!!
            is Response.Failure -> {
                _state.value = PokemonListState(error = response.error)
                -1
            }
        }
    }
}
