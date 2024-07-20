package abs.apps.chatterbox.connect.api

import abs.apps.chatterbox.connect.models.RegisterRequest
import abs.apps.chatterbox.connect.models.RegisterResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response as RetrofitResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.Response

class ApiHelper(private val chatService: ChatService) {

    private val client = OkHttpClient() // Client for WebSocket connections
    fun registerUser(request: RegisterRequest): LiveData<Resource<RegisterResponse>> {
        val result = MutableLiveData<Resource<RegisterResponse>>()
        result.postValue(Resource.Loading())

        chatService.registerClient(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: RetrofitResponse<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        result.postValue(Resource.Success(it))
                    } ?: result.postValue(Resource.Error("Empty response"))
                } else {
                    result.postValue(Resource.Error("Server error: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                result.postValue(Resource.Error("Network failure: ${t.message}"))
            }
        })
        return result
    }

    fun connectWebSocket(token: String, listener: WebSocketListener): WebSocket {
        val request = Request.Builder()
            .url("ws://yourserver.com/ws") // Change this URL to your WebSocket endpoint
            .addHeader("Authorization", "Bearer $token")
            .build()

        return client.newWebSocket(request, listener)
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
