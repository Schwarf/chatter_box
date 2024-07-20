package abs.apps.chatterbox.data

import abs.apps.chatterbox.data.dao.ICredentialsDao
import abs.apps.chatterbox.data.dao.IMessageDao
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Messages::class, Credentials::class], version = 1)
abstract class AppDataBase : RoomDatabase() {
    abstract fun messageDao(): IMessageDao
    abstract fun credentialsDao(): ICredentialsDao
}