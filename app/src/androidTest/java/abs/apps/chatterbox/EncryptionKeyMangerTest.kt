package abs.apps.chatterbox

import abs.apps.chatterbox.data.encryption.IKeyManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.security.KeyStore
import javax.crypto.SecretKey
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class EncryptionKeyManagerInstrumentedTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var keyManager: IKeyManager

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testGetOrCreateKey() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // Use the injected keyManager to get or create a key
        val secretKey: SecretKey = keyManager.getOrCreateKey()

        // Verify the key is not null
        assertNotNull(secretKey)

        // Verify the key is stored in the keystore
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }
        val retrievedKey = keyStore.getKey("ChatterBoxKey", null) as SecretKey
        assertNotNull(retrievedKey)
    }
}
