package info.tduty.telegram2factorauth.telegram

import org.telegram.telegrambots.meta.api.objects.Update

object SelectHandle {
    private val container: BotApiMethodContainer = BotApiMethodContainer.instanse

    @JvmStatic
    fun getHandle(update: Update): BotApiMethodController? {
        val path: String
        return if (update.hasMessage() && update.message.hasText()) {
            path = update.message.text.split(" ").toTypedArray().first().trim { it <= ' ' }
            container.getBotApiMethodController(path) ?: container.getBotApiMethodController("")
        } else if (update.hasCallbackQuery()) {
            path = update.callbackQuery.data.split("/").toTypedArray()[1].trim { it <= ' ' }
            container.getBotApiMethodController(path)
        } else {
            container.getBotApiMethodController("")
        }
    }
}
