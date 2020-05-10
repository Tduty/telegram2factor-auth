package info.tduty.telegram2factorauth.controller

import info.tduty.telegram2factorauth.data.AuthorityDTO
import info.tduty.telegram2factorauth.domain.TokenVerification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.ws.rs.HeaderParam

@RestController
@RequestMapping("/auth")
class AuthRestController @Autowired constructor(
        private val tokenVerification: TokenVerification
) {

    @GetMapping(value = ["/"])
    fun auth(@RequestHeader("token") token: String): AuthorityDTO {
        return tokenVerification.verify(token)
    }
}
