package abs.apps.chatterbox.injection


import abs.apps.chatterbox.data.encryption.EncryptionKeyManager
import abs.apps.chatterbox.data.encryption.IKeyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKeyManager(): IKeyManager {
        return EncryptionKeyManager()
    }
}