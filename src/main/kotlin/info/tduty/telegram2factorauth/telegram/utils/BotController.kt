package info.tduty.telegram2factorauth.telegram.utils

import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class BotController(vararg val value: String = [])
