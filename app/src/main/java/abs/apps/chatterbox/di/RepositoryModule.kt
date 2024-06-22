package abs.apps.chatterbox.di

import abs.apps.chatterbox.data.repositories.IMessageRepository
import abs.apps.chatterbox.data.repositories.MessageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMessageRepository(
        messageRepository: MessageRepository
    ): IMessageRepository
}
