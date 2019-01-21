package accepted.challenge.fenix.com.photogame.app.Models

import com.google.gson.annotations.SerializedName

class LeaderShipModel(@SerializedName("user_id") val userId: String,
                      val likes: Int,
                      val dislikes: Int,
                      val views: Int,
                      val user: User)