package pt.ul.fc.cm.pokefit.utils

sealed class Response<out T> {

    data object Loading: Response<Nothing>()

    class Success<out T>(val data: T?): Response<T>()

    class Failure(val error: String): Response<Nothing>()

}