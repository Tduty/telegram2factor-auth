package info.tduty.telegram2factorauth.repository

import info.tduty.telegram2factorauth.data.AuthorityDB
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AuthorityRepository : JpaRepository<AuthorityDB, String> {

    @Query(value = "SELECT * FROM Authority WHERE phone = ?1", nativeQuery = true)
    fun findByPhone(phone: String): Optional<AuthorityDB>

    fun deleteByExpirationDateBefore(expirationDate: Date)
}