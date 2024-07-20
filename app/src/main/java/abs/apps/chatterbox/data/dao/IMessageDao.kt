package abs.apps.chatterbox.data.dao

import abs.apps.chatterbox.data.Messages
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IMessageDao {
    @Insert
    suspend fun insertMessage(messages: Messages)
    @Query("SELECT * FROM messages ORDER BY timestamp_ms DESC LIMIT :limit OFFSET :offset")
    suspend fun loadMessages(limit: Int, offset: Int): List<Messages>

    @Query("SELECT * FROM messages ORDER BY timestamp_ms DESC LIMIT :limit OFFSET :offset")
    fun loadMessagesFlow(limit: Int, offset: Int): Flow<List<Messages>>

    @Query("SELECT * FROM messages WHERE clientId = :clientId ORDER BY timestamp_ms DESC")
    suspend fun loadMessagesBySender(clientId: Int): List<Messages>

    @Query("SELECT * FROM messages WHERE clientId = :clientId ORDER BY timestamp_ms DESC")
    fun loadMessagesBySenderFlow(clientId: Int): Flow<List<Messages>>

    @Query("SELECT * FROM messages WHERE hash = :hash")
    suspend fun loadMessageByHash(hash: String): Messages?

    @Query("UPDATE messages SET sentToServer = :sentToServer WHERE id = :id")
    suspend fun updateSentToServerStatus(id: Long, sentToServer: Boolean)

    @Query("UPDATE messages SET receivedByServer = :receivedByServer WHERE id = :id")
    suspend fun updateReceivedByServerStatus(id: Long, receivedByServer: Boolean)

    @Query("UPDATE messages SET receivedByClients = :receivedByClients WHERE id = :id")
    suspend fun updateReceivedByClientsStatus(id: Long, receivedByClients: Boolean)

}