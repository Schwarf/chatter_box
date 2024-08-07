package abs.apps.chatterbox.di

import abs.apps.chatterbox.connect.api.ApiHelper
import abs.apps.chatterbox.connect.repositories.IUserRepository
import abs.apps.chatterbox.connect.repositories.UserRepository
import abs.apps.chatterbox.data.dao.ICredentialsDao
import abs.apps.chatterbox.data.dao.IMessageDao
import abs.apps.chatterbox.data.repositories.CredentialsRepository
import abs.apps.chatterbox.data.repositories.ICredentialsRepository
import abs.apps.chatterbox.data.repositories.IMessageRepository
import abs.apps.chatterbox.data.repositories.MessageRepository
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCredentialsRepository(credentialsDao: ICredentialsDao): ICredentialsRepository {
        return CredentialsRepository(credentialsDao)
    }

    @Provides
    fun provideUserRepository(
        apiHelper: ApiHelper,
        @ApplicationContext context: Context,
        credentialsRepository: ICredentialsRepository
    ): IUserRepository {
        return UserRepository(apiHelper, context, credentialsRepository)
    }

    @Provides
    fun provideMessageRepository(
        messageDao: IMessageDao
    ): IMessageRepository {
        return MessageRepository(messageDao)
    }

}