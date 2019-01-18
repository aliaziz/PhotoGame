package accepted.challenge.fenix.com.photogame.Data

import accepted.challenge.fenix.com.photogame.Data.model.ApiDataResponse
import accepted.challenge.fenix.com.photogame.Data.model.ApiMessageResponse
import accepted.challenge.fenix.com.photogame.app.Models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("register")
    fun register(@Field("username") username: String,
                 @Field("email") email: String): Call<ApiDataResponse<UserResponse>>

    @FormUrlEncoded
    @POST("login")
    fun login(@Field("username") username: String): Call<ApiDataResponse<UserResponse>>

    @FormUrlEncoded
    @POST("upload-photo")
    fun uploadPic(@Field("photo") photo: String,
                  @Field("caption") caption: String,
                  @Field("description") description: String,
                  @Field("category") category: String,
                  @Field("location") location: String,
                  @Field("api_token") token: String): Call<ApiMessageResponse>
}