package abs.apps.chatterbox.data.encryption

import javax.crypto.SecretKey

interface IKeyManager {
    fun getOrCreateKey() : SecretKey
}