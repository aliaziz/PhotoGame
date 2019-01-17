package accepted.challenge.fenix.com.photogame.Data.model

/*
** Generic to handle api responses
*
**/
class ApiResponse<T>(val status: String,
                     val response: T)