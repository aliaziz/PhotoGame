package accepted.challenge.fenix.com.photogame.Data.model

import io.realm.annotations.PrimaryKey

open class LocalGameUploadDetails (
        @PrimaryKey
        val pic: String,
        val caption: String,
        val description: String,
        val category: String,
        val location: String
)