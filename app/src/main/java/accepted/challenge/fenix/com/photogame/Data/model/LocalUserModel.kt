package accepted.challenge.fenix.com.photogame.Data.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

class LocalUserModel: RealmObject() {
    @PrimaryKey
    var userName: String = ""
    var userEmail: String = ""
    var userToken: String = ""

}