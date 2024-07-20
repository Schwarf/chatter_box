package abs.apps.chatterbox.data.dao

import abs.apps.chatterbox.data.Credentials
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ICredentialsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCredentials(credentials: Credentials)

    @Query("SELECT * FROM credentials LIMIT 1")
    suspend fun getCredentials(): Credentials?

    @Query("DELETE FROM credentials")
    suspend fun deleteCredentials()
}
