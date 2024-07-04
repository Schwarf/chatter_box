package abs.apps.chatterbox.ui.screens

import abs.apps.chatterbox.data.Message
import abs.apps.chatterbox.ui.viemodels.MessageViewModel
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ChatScreen(
    viewModel: MessageViewModel = hiltViewModel(),
    sender: String
) {
    Log.d("ChatScreen", "ChatScreen called")
    viewModel.loadMessages(sender)
    val messages by viewModel.messages.observeAsState(emptyList())
    var messageText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp) // Ensure space for the input field
        ) {
            MessageList(messages = messages, modifier = Modifier.weight(1f))
        }
        MessageInput(
            messageText = messageText,
            onMessageTextChanged = { messageText = it },
            onSendClick = {
                viewModel.sendMessage(messageText)
                messageText = ""
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(8.dp)
        )
    }
}

@Composable
fun MessageList(messages: List<Message>, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        reverseLayout = true
    ) {
        items(messages) { message -> MessageItem(message) }
    }
}


@Composable
fun MessageItem(message: Message) {
    Text(
        text = message.text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        style = MaterialTheme.typography.bodyMedium
    )

}

@Composable
fun MessageInput(
    messageText: String,
    onMessageTextChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = messageText,
            onValueChange = onMessageTextChanged,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Type a message") }
        )
        IconButton(onClick = onSendClick) {
            Icon(imageVector = Icons.AutoMirrored.Default.Send, contentDescription = "Send",
                tint = Color.Green)
        }
    }
}