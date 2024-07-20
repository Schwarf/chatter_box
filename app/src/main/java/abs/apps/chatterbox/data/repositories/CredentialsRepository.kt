package abs.apps.chatterbox.data.repositories

import abs.apps.chatterbox.data.Credentials
import abs.apps.chatterbox.data.dao.ICredentialsDao
import javax.inject.Inject

class CredentialsRepository @Inject constructor(private val credentialsDao: ICredentialsDao) {
    suspend fun updateCredentials(credentials: Credentials) {
        credentialsDao.insertOrUpdateCredentials(credentials)
    }

    suspend fun getCredentials(): Credentials? {
        return credentialsDao.getCredentials()
    }

    suspend fun deleteCredentials() {
        credentialsDao.deleteCredentials()
    }
}