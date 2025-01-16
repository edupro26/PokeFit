package pt.ul.fc.cm.pokefit.presentation.screens.pokemon.detail

import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pt.ul.fc.cm.pokefit.domain.usecase.PokemonDetail
import pt.ul.fc.cm.pokefit.domain.usecase.UserAccount
import pt.ul.fc.cm.pokefit.utils.Resource

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
        savedStateHandle.get<String>("pokemonId")?.let { id ->
            loadPokemon(id.toInt(), uid)
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