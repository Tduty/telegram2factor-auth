package info.tduty.telegram2factorauth.domain

import info.tduty.telegram2factorauth.data.AuthorityDB
import info.tduty.telegram2factorauth.repository.AuthorityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Contact
import java.util.*
import kotlin.random.Random


@Component
class TokenGenerator @Autowired constructor(
        private val tokenCoder: TokenCoder,
        private val authorityRepository: AuthorityRepository
) {

    companion object {
        private const val TOKEN_COUNT = 6
        private const val EXPIRATION_INCREMENT = 5
    }

    @Value("\${app.host}")
    lateinit var host: String

    fun generate(contact: Contact): String {
        val token = createToken()
        authorityRepository.save(AuthorityDB(
                phone = contact.phoneNumber,
                name = contact.firstName + " " + contact.lastName,
                token = token,
                expirationDate = createExpirationDate()
        ))
        return "${host}${tokenCoder.encode("${contact.phoneNumber},$token")}"
    }

    private fun createExpirationDate(): Date {
        val now = Calendar.getInstance()
        now.add(Calendar.MINUTE, EXPIRATION_INCREMENT)
        return now.time
    }

    private fun createToken(): String {
        val builder = StringBuilder()
        for (i in 0 until TOKEN_COUNT) {
            builder.append(Random.nextInt(9))
        }
        return builder.toString()
    }
}
