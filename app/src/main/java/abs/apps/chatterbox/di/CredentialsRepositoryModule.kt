package abs.apps.chatterbox.di

import abs.apps.chatterbox.data.dao.ICredentialsDao
import abs.apps.chatterbox.data.repositories.CredentialsRepository
import abs.apps.chatterbox.data.repositories.ICredentialsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CredentialsRepositoryModule {
    @Provides
    @Singleton
    fun provideCredentialsRepository(credentialsDao: ICredentialsDao): ICredentialsRepository {
        return CredentialsRepository(credentialsDao)
    }
}