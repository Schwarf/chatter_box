package abs.apps.chatterbox.data.repositories

import abs.apps.chatterbox.data.Message
import kotlinx.coroutines.flow.Flow
interface IMessageRepository {
    suspend fun insertMessage(message: Message)
    suspend fun loadMessages(limit: Int, offset: Int): List<Message>
    fun loadMessagesFlow(limit: Int, offset: Int): Flow<List<Message>>
    suspend fun loadMessagesBySender(sender: String): List<Message>
    fun loadMessagesBySenderFlow(sender: String): Flow<List<Message>>
    suspend fun loadMessageByHash(hash: String): Message?
    suspend fun updateSentToServerStatus(id: Long, sentToServer: Boolean)
    suspend fun updateReceivedByServerStatus(id: Long, receivedByServer: Boolean)
    suspend fun updateReceivedByClientsStatus(id: Long, receivedByClients: Boolean)
}