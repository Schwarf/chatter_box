package abs.apps.chatterbox.ui.viemodels

import abs.apps.chatterbox.connect.api.Resource
import abs.apps.chatterbox.connect.models.RegisterResponse
import abs.apps.chatterbox.connect.repositories.IUserRepository
import abs.apps.chatterbox.data.Credentials
import abs.apps.chatterbox.data.Messages
import abs.apps.chatterbox.data.repositories.ICredentialsRepository
import abs.apps.chatterbox.data.repositories.IMessageRepository
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
    private val userRepository: IUserRepository,
    private val credentialsRepository: ICredentialsRepository
) : ViewModel() {

    private val _credentials = MutableLiveData<Credentials?>()
    val credentials: LiveData<Credentials?> get() = _credentials
    private val _messages = MutableLiveData<List<Messages>>()
    val messages: LiveData<List<Messages>> get() = _messages
    private val _registrationComplete = MutableLiveData<Boolean>(false)
    val registrationComplete: LiveData<Boolean> = _registrationComplete

    init {
        attemptWebSocketConnection()
    }

    // TODO: Inefficient if registration is done first. It would be better then to provide just
    //  the token instead of loading just inserted credentials.
    private fun attemptWebSocketConnection() {
        viewModelScope.launch {
            _credentials.value = credentialsRepository.getCredentials()
            if (_credentials.value == null) {
                Log.d("CREDENTIALS", "Credentials are null")
            } else {
                _registrationComplete.postValue(true)
            }
            _credentials.value?.let {
                userRepository.connectWebSocket(it.token)
            }
        }
    }

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
        registerRequest?.let {
            userRepository.registerUser(it).observeForever { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.data?.let { responseData ->
                            handleSuccess(responseData)
                            viewModelScope.launch {
                                credentialsRepository.updateCredentials(
                                    Credentials(
                                        idAtServer = responseData.id,
                                        userName = responseData.username,
                                        token = responseData.token,
                                        salt = responseData.salt
                                    )
                                )
                                _registrationComplete.postValue(true)
                                attemptWebSocketConnection()
                            }
                        }
                    }

                    is Resource.Error -> handleError(resource.message)
                    is Resource.Loading -> handleLoading()
                }
            }
        } ?: Log.e(TAG, "Registration request is null")
    }

}