package abs.apps.chatterbox.ui.viemodels

import abs.apps.chatterbox.connect.api.Resource
import abs.apps.chatterbox.connect.models.RegisterResponse
import abs.apps.chatterbox.connect.repositories.IUserRepository
import abs.apps.chatterbox.connect.repositories.UserRepository
import abs.apps.chatterbox.data.Messages
import abs.apps.chatterbox.data.repositories.IMessageRepository
import abs.apps.chatterbox.data.repositories.MessageRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import android.util.Log
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageRepository: IMessageRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    private val _messages = MutableLiveData<List<Messages>>()
    val messages: LiveData<List<Messages>> get() = _messages

    fun loadMessages(clientId: Int) {
        viewModelScope.launch {
            _messages.value = messageRepository.loadMessagesBySender(clientId)
        }
    }

    fun sendMessage(messageText: String) {
        viewModelScope.launch {
            val messages = Messages(
                clientId = 1,
                text = messageText,
                timestamp_ms = System.currentTimeMillis(),
                hash = "some_hash_value",
                chatId = "XX"
            )
            messageRepository.insertMessage(messages)
            loadMessages(messages.clientId)

        }
    }

    private val TAG = "UserRegistration"

    private fun handleSuccess(registerResponse: RegisterResponse?) {
        registerResponse?.let {
            Log.d(
                TAG,
                "Registration successful: User ID = ${it.id}, Token = ${it.token}, Username = ${it.username}, Salt = ${it.salt}"
            )
        } ?: Log.d(TAG, "Registration successful, but the response is null")
    }


    private fun handleError(message: String?) {
        Log.e(TAG, "Error during registration: $message")
    }

    private fun handleLoading() {
        Log.i(TAG, "Loading registration process...")
    }

    fun attemptRegistration() {
        val registerRequest = userRepository.loadRegistrationCredentials()
        if (registerRequest != null) {
            userRepository.registerUser(registerRequest).observeForever { resource ->
                // Handle the response here
                when (resource) {
                    is Resource.Success -> handleSuccess(resource.data)
                    is Resource.Error -> handleError(resource.message)
                    is Resource.Loading -> handleLoading()
                }
            }
        }
    }

}