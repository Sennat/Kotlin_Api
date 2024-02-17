sealed class ResultState {
    data class SUCCESS<T>(val response: T) : ResultState()
    data class FAILURE(val error: Exception) : ResultState()
}