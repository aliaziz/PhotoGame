package accepted.challenge.fenix.com.photogame.Data.model

/*
** Generic to handle api responses
*
**/
class ApiDataResponse<T>(val status: String,
                         val response: T)