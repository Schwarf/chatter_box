package abs.apps.chatterbox.connect.repositories

import abs.apps.chatterbox.connect.api.ApiHelper
import abs.apps.chatterbox.connect.api.Resource
import abs.apps.chatterbox.connect.models.RegisterRequest
import abs.apps.chatterbox.connect.models.RegisterResponse
import android.content.Context
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import javax.inject.Inject

class UserRepository @Inject constructor (private val apiHelper: ApiHelper, private val context: Context): IUserRepository {

    override fun registerUser(registerRequest: RegisterRequest): LiveData<Resource<RegisterResponse>>{
    return apiHelper.registerUser(registerRequest)

    }

    override fun loadCredentials(): RegisterRequest? {
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

    override fun connectWebSocket(token: String) {
        // Initialize and manage WebSocket connection
    }
}

