package abs.apps.chatterbox.connect.api

import abs.apps.chatterbox.connect.models.RegisterRequest
import abs.apps.chatterbox.connect.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService {
    @POST("/register")
    fun registerClient(@Body request: RegisterRequest): Call<RegisterResponse>
}