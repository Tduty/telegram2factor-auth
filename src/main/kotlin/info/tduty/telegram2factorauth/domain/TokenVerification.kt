package info.tduty.telegram2factorauth.domain

import info.tduty.telegram2factorauth.data.AuthorityDB
import info.tduty.telegram2factorauth.data.AuthorityDTO
import info.tduty.telegram2factorauth.repository.AuthorityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException

@Component
class TokenVerification @Autowired constructor(
        private val tokenCoder: TokenCoder,
        private val authorityRepository: AuthorityRepository
) {

    fun verify(token: String): AuthorityDTO {
        val tokens = tokenCoder.decode(token).split(",")
        val phone = tokens.getOrNull(0) ?: throw IllegalArgumentException("Token not valid")
        val code = tokens.getOrNull(1) ?: throw IllegalArgumentException("Token not valid")
        val authorityDB = authorityRepository.findByPhone(phone).orElseThrow { throw IllegalArgumentException("Token not valid") }
        return if (authorityDB.token == code) mapToDTO(authorityDB)
        else throw IllegalArgumentException("Token not valid")
    }

    private fun mapToDTO(db: AuthorityDB): AuthorityDTO {
        return AuthorityDTO(
                db.id,
                db.phone,
                db.name
        )
    }
}