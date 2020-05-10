package info.tduty.telegram2factorauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.telegram.telegrambots.ApiContextInitializer

@SpringBootApplication
class Telegram2factorAuthApplication

fun main(args: Array<String>) {
	ApiContextInitializer.init();
	runApplication<Telegram2factorAuthApplication>(*args)
}
