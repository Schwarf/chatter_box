package abs.apps.chatterbox.data.repositories

import abs.apps.chatterbox.data.Message
import abs.apps.chatterbox.data.dao.IMessageDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val messageDao: IMessageDao
) : IMessageRepository {
    override suspend fun insertMessage(message: Message) {
        messageDao.insertMessage(message)
    }

    override suspend fun loadMessages(limit: Int, offset: Int): List<Message> {
        return messageDao.loadMessages(limit, offset)
    }

    override fun loadMessagesFlow(limit: Int, offset: Int): Flow<List<Message>> {
        return messageDao.loadMessagesFlow(limit, offset)
    }

    override suspend fun loadMessagesBySender(sender: String): List<Message> {
        return messageDao.loadMessagesBySender(sender)
    }

    override fun loadMessagesBySenderFlow(sender: String): Flow<List<Message>> {
        return messageDao.loadMessagesBySenderFlow(sender)
    }

    override suspend fun loadMessageByHash(hash: String): Message? {
        return messageDao.loadMessageByHash(hash)
    }

    override suspend fun updateSentToServerStatus(id: Long, sentToServer: Boolean) {
        messageDao.updateSentToServerStatus(id, sentToServer)
    }

    override suspend fun updateReceivedByServerStatus(id: Long, receivedByServer: Boolean) {
        messageDao.updateReceivedByServerStatus(id, receivedByServer)
    }

    override suspend fun updateReceivedByClientsStatus(id: Long, receivedByClients: Boolean) {
        messageDao.updateReceivedByClientsStatus(id, receivedByClients)
    }
}
