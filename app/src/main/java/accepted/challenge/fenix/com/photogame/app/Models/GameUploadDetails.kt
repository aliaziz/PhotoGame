package accepted.challenge.fenix.com.photogame.app.Models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GameUploadDetails(
        @SerializedName("photo_url") val pic: String,
        val caption: String,
        val description: String?,
        val category: String?,
        val location: String?,
        val likes: Int? = 0,
        val dislikes: Int? = 0,
        val views: Int? = 0,
        @SerializedName("id") val photoId: Int = 0
): Serializable
