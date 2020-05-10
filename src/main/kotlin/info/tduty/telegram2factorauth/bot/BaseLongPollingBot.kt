package info.tduty.telegram2factorauth.bot

import info.tduty.telegram2factorauth.telegram.BotApiMethodController
import info.tduty.telegram2factorauth.telegram.SelectHandle
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import java.io.Serializable

@Component
class BaseLongPollingBot : TelegramLongPollingBot() {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BotApiMethodController::class.java)
    }

    @Value("\${bot.username}")
    lateinit var username: String
    @Value("\${bot.token}")
    lateinit var token: String

    override fun getBotUsername() = username

    override fun getBotToken() = token

    override fun onUpdateReceived(update: Update?) {
        try {
            if (update == null) return
            SelectHandle.getHandle(update)?.process(update)?.forEach { method ->
                execute(method as BotApiMethod<Serializable>)
            }
        } catch (ex: Exception) {
            LOGGER.error("Exception when sending $update", ex)
        }
    }
}
