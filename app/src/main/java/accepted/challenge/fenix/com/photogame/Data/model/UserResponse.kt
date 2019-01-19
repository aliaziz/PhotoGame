package accepted.challenge.fenix.com.photogame.Data.model

import accepted.challenge.fenix.com.photogame.app.Models.User
import com.google.gson.annotations.SerializedName

class UserResponse(@SerializedName("user") val userData: User)
