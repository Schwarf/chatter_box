package abs.apps.chatterbox.data.dao

import abs.apps.chatterbox.data.Message
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IMessageDao {
    @Insert
    suspend fun insertMessage(message: Message)
    @Query("SELECT * FROM messages ORDER BY timestamp DESC LIMIT :limit OFFSET :offset")
    suspend fun loadMessages(limit: Int, offset: Int): List<Message>

    @Query("SELECT * FROM messages ORDER BY timestamp DESC LIMIT :limit OFFSET :offset")
    suspend fun loadMessagesFlow(limit: Int, offset: Int): Flow<List<Message>>

    @Query("SELECT * FROM messages WHERE sender = :sender ORDER BY timestamp DESC")
    suspend fun loadMessagesBySender(sender: String): List<Message>

    @Query("SELECT * FROM messages WHERE sender = :sender ORDER BY timestamp DESC")
    suspend fun loadMessagesBySenderFlow(sender: String): Flow<List<Message>>

    @Query("SELECT * FROM messages WHERE hash = :hash")
    suspend fun loadMessageByHash(hash: String): Message?
}