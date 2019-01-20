package accepted.challenge.fenix.com.photogame.Data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LocalGameUploadDetails (
        @PrimaryKey
        var pic: String = "",
        var caption: String = "",
        var description: String? = "",
        var category: String? = "",
        var location: String? = ""
): RealmObject()