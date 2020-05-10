package info.tduty.telegram2factorauth.domain

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Contact

@Component
class TokenGenerator {

    @Value("\${app.host}")
    lateinit var host: String

    fun generate(contact: Contact): String {
        return "${host}ODkwNDUyODMzNTYrMTIzNDU2"
    }
}
