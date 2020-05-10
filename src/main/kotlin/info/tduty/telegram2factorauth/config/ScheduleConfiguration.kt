package info.tduty.telegram2factorauth.config

import info.tduty.telegram2factorauth.repository.AuthorityRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import java.util.*
import javax.transaction.Transactional

@Configuration
@EnableScheduling
class ScheduleConfiguration @Autowired constructor(
        private val authorityRepository: AuthorityRepository
) {

    @Scheduled(fixedDelay = 1000)
    @Transactional
    fun purgeExpiredVerifications() {
        authorityRepository.deleteByExpirationDateBefore(Date())
    }
}