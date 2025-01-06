package pt.ul.fc.cm.pokefit.utils

sealed class Resource<out T> {

    data object Loading : Resource<Nothing>()

    class Success<out T>(val data: T?) : Resource<T>()

    class Failure(val error: String) : Resource<Nothing>()

}
