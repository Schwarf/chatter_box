package abs.apps.chatterbox.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "credentials")
data class Credentials(
    @PrimaryKey(autoGenerate = false) val idAtServer: Int,
    @ColumnInfo(name = "user_name") val userName: String,
    @ColumnInfo(name = "token") val token: String,
    @ColumnInfo(name = "salt") val salt: String
)


