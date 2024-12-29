package pt.ul.fc.cm.pokefit.presentation.screens.pokemon

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import pt.ul.fc.cm.pokefit.domain.usecase.GetPokemonList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val getPokemonList: GetPokemonList
) : ViewModel() {

    private val _state = mutableStateOf(PokemonListState())
    val state: State<PokemonListState> = _state

    init {
        getPokemon()
    }

    private fun getPokemon() {
        getPokemonList().onEach { result ->
            /* TODO: Better result state handling */
            _state.value = PokemonListState(pokemon = result)
        }.launchIn(viewModelScope)
    }
}
