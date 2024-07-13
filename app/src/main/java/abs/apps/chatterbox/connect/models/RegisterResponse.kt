package abs.apps.chatterbox.connect.models

data class RegisterResponse(val id: Int, val username: String, val token: String, val salt: String)
