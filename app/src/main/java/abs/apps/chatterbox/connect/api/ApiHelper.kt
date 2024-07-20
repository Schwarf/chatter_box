package abs.apps.chatterbox.connect.api

import abs.apps.chatterbox.connect.models.RegisterRequest
import abs.apps.chatterbox.connect.models.RegisterResponse
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiHelper(private val chatService: ChatService) {

    fun registerUser(request: RegisterRequest): LiveData<Resource<RegisterResponse>> {
        val result = MutableLiveData<Resource<RegisterResponse>>()
        result.postValue(Resource.Loading())

        chatService.registerClient(request).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
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
}

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}
