package abs.apps.chatterbox.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "messages",
    indices = [
        Index(value = ["timestamp"]),
        Index(value = ["sender"]),
        Index(value = ["hash"]),
        Index(value = ["sender", "timestamp"])
    ]
)
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "sender") val sender: String,
    @ColumnInfo(name = "message") val message: String,
    @ColumnInfo(name = "timestamp") val timestamp: Long,
    @ColumnInfo(name = "hash") val hash: String,
    @ColumnInfo(name = "sentToServer") val sentToServer: Boolean = false,
    @ColumnInfo(name = "receivedByServer") val receivedByServer: Boolean = false,
    @ColumnInfo(name = "receivedByClients") val receivedByClients: Boolean = false,
)