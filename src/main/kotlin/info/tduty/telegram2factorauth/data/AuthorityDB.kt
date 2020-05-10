package info.tduty.telegram2factorauth.data

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "Authority")
data class AuthorityDB(
        @Id
        @GeneratedValue(generator = "system-uuid")
        @GenericGenerator(name = "system-uuid", strategy = "uuid")
        var id: String = "",

        @Column(name = "phone", unique = true)
        var phone: String,

        @Column(name = "full_name")
        var name: String,

        @Column(name = "token")
        var token: String,

        @Column(name = "expiration_date")
        var expirationDate: Date
)
