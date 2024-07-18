package abs.apps.chatterbox.connect.repositories

import RetrofitInstance.chatService
import abs.apps.chatterbox.connect.api.ApiHelper
import abs.apps.chatterbox.connect.api.Resource
import abs.apps.chatterbox.connect.models.RegisterRequest
import abs.apps.chatterbox.connect.models.RegisterResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val apiHelper: ApiHelper) {

    fun registerUser(registerRequest: RegisterRequest): LiveData<Resource<RegisterResponse>>{
    return apiHelper.registerUser(registerRequest)
}

    fun connectWebSocket(token: String) {
        // Initialize and manage WebSocket connection
    }
}

