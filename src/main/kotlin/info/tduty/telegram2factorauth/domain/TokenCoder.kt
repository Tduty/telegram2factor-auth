package info.tduty.telegram2factorauth.domain

import org.springframework.stereotype.Component
import java.util.*

@Component
class TokenCoder {

    fun encode(token: String): String {
        return Base64.getEncoder().encodeToString(token.toByteArray())
    }

    fun decode(token: String): String {
        return String(Base64.getDecoder().decode(token))
    }
}