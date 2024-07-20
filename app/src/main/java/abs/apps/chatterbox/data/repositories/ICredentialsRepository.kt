package abs.apps.chatterbox.data.repositories

import abs.apps.chatterbox.data.Credentials

interface ICredentialsRepository {
    suspend fun updateCredentials(credentials: Credentials)
    suspend fun getCredentials(): Credentials?
    suspend fun deleteCredentials()
}