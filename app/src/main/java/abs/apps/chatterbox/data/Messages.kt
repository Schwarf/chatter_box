package abs.apps.chatterbox.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages",
    indices = [
        Index(value = ["timestamp"]),
        Index(value = ["chatId"]),
        Index(value = ["hash"]),
        Index(value = ["chatId", "timestamp"])
    ]
)
    data class Messages(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "chatId") val chatId: Int,
    @ColumnInfo(name = "clientId") val clientId: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "sentToServer") val sentToServer: Boolean = false,
    @ColumnInfo(name = "receivedByServer") val receivedByServer: Boolean = false,
    @ColumnInfo(name = "receivedByClients") val receivedByClients: Boolean = false,
    )