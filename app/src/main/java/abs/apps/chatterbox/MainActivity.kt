package abs.apps.chatterbox

import abs.apps.chatterbox.data.AppDataBase
import abs.apps.chatterbox.data.encryption.EncryptionKeyManager
import abs.apps.chatterbox.data.encryption.IKeyManager
import abs.apps.chatterbox.ui.screens.ChatScreen
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
import android.app.Application
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.room.Room
import dagger.hilt.android.AndroidEntryPoint
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Inject

    @AndroidEntryPoint
    class MainActivity : ComponentActivity() {

        @Inject lateinit var keyManager: IKeyManager
        @Inject lateinit var db: AppDataBase
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            enableEdgeToEdge()
            setContent {
                ChatClientPrototypeTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Log.d("MainActivity", "Entered Surface!")
                        ChatScreen(sender = "User")
                    }
                //                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
    //                    Greeting(
    //                        name = "Android",
    //                        modifier = Modifier.padding(innerPadding)
    //                    )
    //                }
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