package abs.apps.chatterbox.di

import abs.apps.chatterbox.data.AppDataBase
import abs.apps.chatterbox.data.dao.ICredentialsDao
import abs.apps.chatterbox.data.dao.IMessageDao
import abs.apps.chatterbox.data.encryption.EncryptionKeyManager
import abs.apps.chatterbox.data.encryption.IKeyManager
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        keyManager: IKeyManager
    ): AppDataBase {

        SQLiteDatabase.loadLibs(context)
        val secretKey = keyManager.getOrCreateKey()
        val passphrase = secretKey.encoded
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "chatterbox.db"
        ).openHelperFactory(factory).build()
    }

    @Provides
    fun provideMessageDao(database: AppDataBase): IMessageDao {
        return database.messageDao()
    }

    @Provides
    fun provideCredentialsDao(database: AppDataBase): ICredentialsDao {
        return database.credentialsDao()
    }

    @Provides
    @Singleton
    fun provideKeyManager(): IKeyManager {
        return EncryptionKeyManager()
    }
}