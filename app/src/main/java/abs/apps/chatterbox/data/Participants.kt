package abs.apps.chatterbox.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "participants")
data class Participants(
    @PrimaryKey(autoGenerate = true) val participantId: Int = 0,
    val chatId: String,
    val participantIdentifier: String
)


