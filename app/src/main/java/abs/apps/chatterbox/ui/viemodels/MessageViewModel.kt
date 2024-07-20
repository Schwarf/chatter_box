package abs.apps.chatterbox.ui.viemodels

import abs.apps.chatterbox.connect.api.Resource
import abs.apps.chatterbox.connect.models.RegisterResponse
import abs.apps.chatterbox.connect.repositories.UserRepository
import abs.apps.chatterbox.data.Messages
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
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    init {
        val registerRequest = userRepository.loadRegistrationCredentials()
    }

    private val _messages = MutableLiveData<List<Messages>>()
    val messages: LiveData<List<Messages>> get() = _messages

    fun loadMessages(sender: String) {
        viewModelScope.launch {
            _messages.value = messageRepository.loadMessagesBySender(sender)
        }
    }

    fun sendMessage(messageText: String) {
        viewModelScope.launch {
            val messages = Messages(
                sender = "Alice",
                text = messageText,
                timestamp = System.currentTimeMillis(),
                hash = "some_hash_value",
                chatId = "XX"
            )
            messageRepository.insertMessage(messages)
            loadMessages(messages.sender)
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