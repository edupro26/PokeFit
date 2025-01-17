package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import pt.ul.fc.cm.pokefit.domain.usecase.PokemonDetail
import pt.ul.fc.cm.pokefit.domain.usecase.UserAccount
import pt.ul.fc.cm.pokefit.utils.Constants.PARAM_POKEMON_ID
import pt.ul.fc.cm.pokefit.utils.Resource
import pt.ul.fc.cm.pokefit.utils.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    userAccount: UserAccount,
    private val pokemonDetail: PokemonDetail
) : ViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    private val uid = userAccount.getCurrentUser()!!.uid

    init {
        savedStateHandle.get<String>(PARAM_POKEMON_ID)?.let { id ->
            loadPokemon(id.toInt(), uid)
        }
    }

    fun selectPokemon(
        id: Int,
        context: Context,
        popStack: () -> Unit
    ) = viewModelScope.launch {
        when (val response = pokemonDetail.selectPokemon(id, uid)) {
            is Response.Success -> {
                popStack()
                Toast.makeText(
                    context, "Pokemon selected", Toast.LENGTH_SHORT
                ).show()
            }
            is Response.Failure -> {
                Toast.makeText(
                    context, response.error, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun loadPokemon(id: Int, uid: String) {
        pokemonDetail.loadPokemon(id, uid).onEach { resource ->
            when (resource) {
                is Resource.Loading -> {
                    _state.value = DetailState(isLoading = true)
                }
                is Resource.Success -> {
                    _state.value = DetailState(pokemon = resource.data)
                }
                is Resource.Failure -> {
                    _state.value = DetailState(error = resource.error)
                }
            }
        }.launchIn(viewModelScope)
    }
}