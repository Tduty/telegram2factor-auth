package info.tduty.telegram2factorauth.telegram

import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText

enum class BotRequestMethod(val path: String) {

    MSG(SendMessage.PATH),
    EDIT(EditMessageText.PATH)
}
