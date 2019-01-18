package accepted.challenge.fenix.com.photogame.app.Models

import java.io.Serializable

class GameUploadDetails(
        val pic: String,
        val caption: String,
        val description: String?,
        val category: String?,
        val location: String?
): Serializable
