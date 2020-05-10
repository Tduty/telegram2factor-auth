package info.tduty.telegram2factorauth.controller

import info.tduty.telegram2factorauth.domain.TokenGenerator
import info.tduty.telegram2factorauth.telegram.utils.BotController
import info.tduty.telegram2factorauth.telegram.utils.BotRequestMapping
import org.springframework.beans.factory.annotation.Autowired
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Contact
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@BotController
class AuthBotController @Autowired constructor(
        private val tokenGenerator: TokenGenerator
) {

    @BotRequestMapping(value = ["/start", "/auth"])
    fun ok(update: Update): SendMessage {
        return createMessagePermissionContact(update.message.chatId)
    }

    @BotRequestMapping(value = [""])
    fun reg(update: Update): SendMessage {
        return if (update.message.hasContact()) {
            SendMessage()
                    .setChatId(update.message.chatId)
                    .setText("To complete the registration, click on the button and open the link in the application")
                    .setReplyMarkup(createInlineKeyboard(update.message.contact))
        } else {
            createMessagePermissionContact(update.message.chatId)
        }
    }

    private fun createMessagePermissionContact(chatId: Long): SendMessage {
        return SendMessage()
                .setChatId(chatId)
                .setText("Hello. Give us access to your number so we can register you in the application")
                .setReplyMarkup(createContactKeyboardMarkup())
    }

    private fun createContactKeyboardMarkup(): ReplyKeyboard {
        val reply = ReplyKeyboardMarkup()
        reply.keyboard = listOf(createContactKeyboard())
        reply.resizeKeyboard = true
        return reply
    }

    private fun createContactKeyboard(): KeyboardRow {
        val row = KeyboardRow()
        val button = KeyboardButton("Give access to phone number")
        button.requestContact = true
        row.add(button)
        return row
    }

    private fun createInlineKeyboard(contact: Contact): InlineKeyboardMarkup {
        val inline = InlineKeyboardMarkup()
        val button = InlineKeyboardButton()
        button.text = "Open app"
        button.url = tokenGenerator.generate(contact)
        inline.keyboard = listOf(listOf(button))
        return inline
    }
}
