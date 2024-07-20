package abs.apps.chatterbox.di

import abs.apps.chatterbox.connect.api.ApiHelper
import abs.apps.chatterbox.connect.repositories.UserRepository
import abs.apps.chatterbox.data.repositories.ICredentialsRepository
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UserRepositoryModule {

    @Provides
    fun provideUserRepository(
        apiHelper: ApiHelper,
        @ApplicationContext context: Context,
        credentialsRepository: ICredentialsRepository
    ): UserRepository {
        return UserRepository(apiHelper, context, credentialsRepository)
    }
}