package accepted.challenge.fenix.com.photogame.Data

import accepted.challenge.fenix.com.photogame.Data.model.*
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

    @GET("photos")
    fun fetchPhotos(@Query("api_token") token: String): Call<ApiDataResponse<PhotoResponse>>

    @FormUrlEncoded
    @POST("like")
    fun likePic(@Field("id") photoId: Int,
                @Field("api_token") token: String): Call<ApiMessageResponse>

    @FormUrlEncoded
    @POST("dislike")
    fun dislikePic(@Field("id") photoId: Int,
                   @Field("api_token") token: String): Call<ApiMessageResponse>

    @GET("leadership-board")
    fun getLeadershipBoard(@Query("api_token") token: String)
            : Call<ApiDataResponse<LeadershipResponse>>
}