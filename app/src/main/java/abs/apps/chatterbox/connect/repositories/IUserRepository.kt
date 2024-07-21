package abs.apps.chatterbox.connect.repositories

import abs.apps.chatterbox.connect.api.Resource
import abs.apps.chatterbox.connect.models.RegisterRequest
import abs.apps.chatterbox.connect.models.RegisterResponse
import androidx.lifecycle.LiveData

interface IUserRepository {
    fun registerUser(registerRequest: RegisterRequest): LiveData<Resource<RegisterResponse>>
    fun loadRegistrationCredentials(): RegisterRequest?
    fun connectWebSocket(token: String)
    fun sendWebSocketMessage(jsonFormatMessage : String)
}
