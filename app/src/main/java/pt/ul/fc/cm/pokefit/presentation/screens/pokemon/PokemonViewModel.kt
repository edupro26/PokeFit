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
    private val userAccount: UserAccount,
    private val pokemonList: PokemonList
) : ViewModel() {

    private val _state = mutableStateOf(PokemonListState())
    val state: State<PokemonListState> = _state

    init {
        val uid = userAccount.getCurrentUser()!!.uid
        initListState(uid)
    }

    fun chooseStarterPokemon(pokemon: Pokemon) = viewModelScope.launch {
        _state.value = PokemonListState(isLoading = true)
        val uid = userAccount.getCurrentUser()!!.uid
        val response = pokemonList.saveStarterPokemon(uid, pokemon)
        when (response) {
            is Response.Success -> { loadPokemonList() }
            is Response.Failure -> {
                _state.value = PokemonListState(error = response.error)
            }
        }
    }

    private fun loadPokemonList() {
        pokemonList.loadPokemonList().onEach { resource ->
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

    private fun initListState(uid: String) {
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
