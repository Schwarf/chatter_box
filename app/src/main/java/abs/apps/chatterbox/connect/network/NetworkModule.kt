import abs.apps.chatterbox.connect.api.ApiHelper
import abs.apps.chatterbox.connect.api.ChatService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideChatService(): ChatService = RetrofitInstance.chatService

    @Singleton
    @Provides
    fun provideApiHelper(chatService: ChatService): ApiHelper = ApiHelper(chatService)
}