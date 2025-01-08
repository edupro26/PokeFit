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
import pt.ul.fc.cm.pokefit.utils.Constants.POKEMON_COUNT
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

    private val listSize = POKEMON_COUNT

    init { initListState() }

    fun chooseStarterPokemon(pokemon: Pokemon) = viewModelScope.launch {
        _state.value = PokemonListState(isLoading = true)
        val response = pokemonList.saveStarterPokemon(uid, pokemon)
        when (response) {
            is Response.Success -> { loadPokemonList() }
            is Response.Failure -> {
                _state.value = PokemonListState(error = response.error)
            }
        }
    }

    private fun loadPokemonList() {
        pokemonList.loadUserPokemon(uid).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = PokemonListState(isLoading = true)
                }
                is Resource.Success -> {
                    if (resource.data.size < POKEMON_COUNT) {
                        _state.value = _state.value.copy(pokemon = resource.data)
                        loadLockedPokemon()
                    } else {
                        _state.value = PokemonListState(pokemon = resource.data)
                    }
                }
                is Resource.Failure -> {
                    _state.value = PokemonListState(error = resource.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadLockedPokemon() {
        pokemonList.loadLockedPokemon(listSize).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    val list = _state.value.pokemon
                    _state.value = PokemonListState(
                        pokemon = (list + resource.data).distinctBy { it.id }
                    )
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
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        pokemon = resource.data
                    )
                }
                is Resource.Failure -> {
                    _state.value = PokemonListState(error = resource.error)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun initListState() {
        pokemonList.getUserPokemonCount(uid).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = PokemonListState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = _state.value.copy(isChoosing = resource.data == 0)
                    if (_state.value.isChoosing) {
                        loadStarterPokemon()
                    } else {
                        loadPokemonList()
                    }
                }
                is Resource.Failure -> {
                    _state.value = PokemonListState(error = resource.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}
