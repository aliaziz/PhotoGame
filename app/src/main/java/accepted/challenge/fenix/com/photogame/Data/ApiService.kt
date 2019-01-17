package accepted.challenge.fenix.com.photogame.Data

import accepted.challenge.fenix.com.photogame.Data.model.ApiResponse
import accepted.challenge.fenix.com.photogame.app.Models.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("pics")
    fun getExams(@Query("api_token") token: String): Call<ApiResponse<User>>
}