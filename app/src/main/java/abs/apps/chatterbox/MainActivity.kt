package abs.apps.chatterbox

import abs.apps.chatterbox.data.AppDataBase
import abs.apps.chatterbox.data.encryption.EncryptionKeyManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import abs.apps.chatterbox.ui.theme.ChatClientPrototypeTheme
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SQLiteDatabase.loadLibs(this)
        val secretKey = EncryptionKeyManager.getOrCreateKey()
        val passphrase = secretKey.encoded
        val factory = SupportFactory(passphrase)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, "chatterbox.db"
        ).openHelperFactory(factory)
            .build()

        enableEdgeToEdge()
        setContent {
            ChatClientPrototypeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ChatClientPrototypeTheme {
        Greeting("Android")
    }
}