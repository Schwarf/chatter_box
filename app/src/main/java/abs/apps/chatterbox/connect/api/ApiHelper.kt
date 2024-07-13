package abs.apps.chatterbox.connect.api

import abs.apps.chatterbox.connect.models.RegisterRequest

class ApiHelper(private val chatService: ChatService) {
    fun registerClient(request: RegisterRequest) = chatService.registerClient(request)
}