package abs.apps.chatterbox.data.repositories

import abs.apps.chatterbox.data.Messages
import kotlinx.coroutines.flow.Flow

interface IMessageRepository {
    suspend fun insertMessage(messages: Messages)
    suspend fun loadMessages(limit: Int, offset: Int): List<Messages>
    fun loadMessagesFlow(limit: Int, offset: Int): Flow<List<Messages>>
    suspend fun loadMessagesBySender(clientId: Int): List<Messages>
    fun loadMessagesBySenderFlow(clientId: Int): Flow<List<Messages>>
    suspend fun loadMessageByHash(hash: String): Messages?
    suspend fun updateSentToServerStatus(id: Long, sentToServer: Boolean)
    suspend fun updateReceivedByServerStatus(id: Long, receivedByServer: Boolean)
    suspend fun updateReceivedByClientsStatus(id: Long, receivedByClients: Boolean)
}