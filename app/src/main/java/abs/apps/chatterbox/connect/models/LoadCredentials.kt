package abs.apps.chatterbox.connect.models

import android.content.Context
import com.google.gson.Gson

fun loadCredentials(context: Context): RegisterRequest? {
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
