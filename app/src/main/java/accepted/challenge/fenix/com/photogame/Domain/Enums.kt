package accepted.challenge.fenix.com.photogame.Domain

enum class ErrorMessages {
    INVALID_CREDENTIALS,
    LOAD_ERROR,
    NO_MORE_DATA,
    LIKE_ERROR,
    DISLIKE_ERROR
}

enum class GeneralMessages {
    SUCCESS
}

enum class LoginType {
    Login, Register
}

enum class GameUpdateType {
    LIKE, DISLIKE, VIEW
}