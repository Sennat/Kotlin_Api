sealed class ResultState {
    data class SUCCESS<Bible>(val response: Bible) : ResultState()
    data class FAILURE(val error: Exception) : ResultState()
}