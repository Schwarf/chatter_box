package abs.apps.chatterbox.connect.repositories

import RetrofitInstance.chatService
import abs.apps.chatterbox.connect.api.ApiHelper
import abs.apps.chatterbox.connect.models.RegisterRequest
import abs.apps.chatterbox.connect.models.RegisterResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(private val apiHelper: ApiHelper) {

    fun registerUser(registerRequest: RegisterRequest): LiveData<Resource<RegisterResponse>> {
        val result = MutableLiveData<Resource<RegisterResponse>>()

        result.postValue(Resource.Loading())  // Signal that the loading process has started

        chatService.registerClient(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    // If the response is successful, post the success resource with data
                    response.body()?.let {
                        result.postValue(Resource.Success(it))
                    } ?: result.postValue(Resource.Error("Empty response"))
                } else {
                    // Handle the case where the server responds with an error
                    result.postValue(Resource.Error("Error: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                // Handle network failure, post the error resource with message
                result.postValue(Resource.Error("Network failure: ${t.message}"))
            }
        })

        return result
    }

    fun connectWebSocket(token: String) {
        // Initialize and manage WebSocket connection
    }
}

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

