import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import abs.apps.chatterbox.data.AppDataBase
import abs.apps.chatterbox.data.dao.IMessageDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "chatterbox.db"
        ).build()
    }

    @Provides
    fun provideMessageDao(database: AppDataBase): IMessageDao {
        return database.messageDao()
    }
}