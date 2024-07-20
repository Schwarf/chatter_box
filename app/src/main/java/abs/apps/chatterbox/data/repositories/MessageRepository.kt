package abs.apps.chatterbox.data.repositories

import abs.apps.chatterbox.data.Messages
import abs.apps.chatterbox.data.dao.IMessageDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MessageRepository @Inject constructor(
    private val messageDao: IMessageDao
) : IMessageRepository {
    override suspend fun insertMessage(messages: Messages) {
        messageDao.insertMessage(messages)
    }

    override suspend fun loadMessages(limit: Int, offset: Int): List<Messages> {
        return messageDao.loadMessages(limit, offset)
    }

    override fun loadMessagesFlow(limit: Int, offset: Int): Flow<List<Messages>> {
        return messageDao.loadMessagesFlow(limit, offset)
    }

    override suspend fun loadMessagesBySender(clientId: Int): List<Messages> {
        return messageDao.loadMessagesBySender(clientId)
    }

    override fun loadMessagesBySenderFlow(clientId: Int): Flow<List<Messages>> {
        return messageDao.loadMessagesBySenderFlow(clientId)
    }

    override suspend fun loadMessageByHash(hash: String): Messages? {
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
