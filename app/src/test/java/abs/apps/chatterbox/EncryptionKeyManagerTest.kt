package abs.apps.chatterbox

import abs.apps.chatterbox.data.encryption.IKeyManager
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import org.junit.Assert.assertEquals

class EncryptionKeyManagerTest {

    private lateinit var mockKeyManager: IKeyManager

    @Before
    fun setup() {
        mockKeyManager = mock(IKeyManager::class.java)
    }

    @Test
    fun testGetOrCreateKey_existingKey() {
        // Mock SecretKey
        val mockSecretKey = mock(SecretKey::class.java)

        // Set up the mock behavior
        `when`(mockKeyManager.getOrCreateKey()).thenReturn(mockSecretKey)

        // Test getOrCreateKey
        val resultKey = mockKeyManager.getOrCreateKey()
        assertEquals(mockSecretKey, resultKey)
    }

    @Test
    fun testGetOrCreateKey_newKey() {
        // Mock SecretKey
        val mockSecretKey = SecretKeySpec(ByteArray(16), "AES")

        // Set up the mock behavior
        `when`(mockKeyManager.getOrCreateKey()).thenReturn(mockSecretKey)

        // Test getOrCreateKey
        val resultKey = mockKeyManager.getOrCreateKey()
        assertEquals(mockSecretKey, resultKey)
    }
}
