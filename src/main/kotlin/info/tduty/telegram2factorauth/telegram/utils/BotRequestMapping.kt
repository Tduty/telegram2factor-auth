package info.tduty.telegram2factorauth.telegram.utils

import info.tduty.telegram2factorauth.telegram.BotRequestMethod


@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class BotRequestMapping(
        vararg val value: String = [],
        val method: Array<BotRequestMethod> = [BotRequestMethod.MSG]
)
