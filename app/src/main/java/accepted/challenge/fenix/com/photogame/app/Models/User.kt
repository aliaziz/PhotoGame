package accepted.challenge.fenix.com.photogame.app.Models

import com.google.gson.annotations.SerializedName

class User(@SerializedName("username") val userName: String,
           @SerializedName("api_token") val token: String,
           @SerializedName("email") val userEmail: String)