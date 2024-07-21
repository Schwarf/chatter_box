package abs.apps.chatterbox.connect.repositories

import abs.apps.chatterbox.connect.api.ApiHelper
import abs.apps.chatterbox.connect.api.Resource
import abs.apps.chatterbox.connect.models.RegisterRequest
import abs.apps.chatterbox.connect.models.RegisterResponse
import abs.apps.chatterbox.data.Credentials
import abs.apps.chatterbox.data.repositories.ICredentialsRepository
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiHelper: ApiHelper, private val context: Context,
    private val credentialsRepository: ICredentialsRepository
) : IUserRepository {

    private var webSocket: WebSocket? = null

    override fun registerUser(registerRequest: RegisterRequest): LiveData<Resource<RegisterResponse>> {
        val result = MutableLiveData<Resource<RegisterResponse>>()
        result.postValue(Resource.Loading())

        apiHelper.registerUser(registerRequest).observeForever { resource ->
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let {
                        CoroutineScope(Dispatchers.IO).launch {
                            storeCredentials(it)
                        }
                        result.postValue(Resource.Success(it))
                    } ?: result.postValue(Resource.Error("Empty response"))
                }

                is Resource.Error -> {
                    result.postValue(Resource.Error(resource.message ?: "Unknown error"))
                }

                is Resource.Loading -> {
                    // Optionally handle loading state
                }
            }
        }
        return result
    }

    override fun loadRegistrationCredentials(): RegisterRequest? {
        try {
            context.assets.open("credentials.json").use { inputStream ->
                val json = inputStream.bufferedReader().use { it.readText() }
                return Gson().fromJson(json, RegisterRequest::class.java)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun storeCredentials(registerResponse: RegisterResponse) {
        CoroutineScope(Dispatchers.IO).launch {
            credentialsRepository.updateCredentials(
                Credentials(
                    idAtServer = registerResponse.id,
                    userName = registerResponse.username,
                    token = registerResponse.token,
                    salt = registerResponse.salt
                )
            )
        }
    }

    override fun connectWebSocket(token: String) {
        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("WebSocket", "Connected to WS")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d("WebSocket", "Receiving : $text")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                webSocket.close(1000, null)
                Log.d("WebSocket", "Closing : $code / $reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.e("WebSocket", "Error : " + t.message)
            }
        }
        webSocket = apiHelper.connectWebSocket(token, listener)
    }

    override fun sendWebSocketMessage(message: String) {
        if (webSocket != null && webSocket!!.send(message)) {
            Log.d("WebSocket", "Message sent: $message")
        } else {
            Log.e("WebSocket", "Failed to send message or WebSocket not connected")
        }
    }
}


