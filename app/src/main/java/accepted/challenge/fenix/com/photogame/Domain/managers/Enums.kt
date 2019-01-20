package accepted.challenge.fenix.com.photogame.Domain.managers

enum class ErrorMessages {
    INVALID_CREDENTIALS,
    LOAD_ERROR,
    NO_MORE_DATA,
    LIKE_ERROR,
    DISLIKE_ERROR,
    MISSING_DATA
}

enum class GeneralMessages {
    SUCCESS
}

enum class LoginType {
    Login, Register
}

enum class GameUpdateType {
    LIKE, DISLIKE
}