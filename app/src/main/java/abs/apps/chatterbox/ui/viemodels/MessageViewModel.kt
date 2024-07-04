package abs.apps.chatterbox.ui.viemodels

import abs.apps.chatterbox.data.Message
import abs.apps.chatterbox.data.repositories.MessageRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val repository: MessageRepository
) : ViewModel() {

    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    fun loadMessages(sender: String) {
        viewModelScope.launch {
            _messages.value = repository.loadMessagesBySender(sender)
        }
    }

    fun sendMessage(messageText: String) {
        viewModelScope.launch {
            val message = Message(
                sender = "Alice",
                text = messageText,
                timestamp = System.currentTimeMillis(),
                hash = "some_hash_value"
            )
            repository.insertMessage(message)
            loadMessages(message.sender)
        }
    }
}