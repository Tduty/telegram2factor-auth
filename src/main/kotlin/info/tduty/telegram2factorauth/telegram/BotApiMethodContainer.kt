package info.tduty.telegram2factorauth.telegram

import info.tduty.telegram2factorauth.telegram.utils.BotApiMethodContainerException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*

class BotApiMethodContainer private constructor(
        private val controllerMap: HashMap<String, BotApiMethodController> = hashMapOf()
) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BotApiMethodContainer::class.java)
        val instanse: BotApiMethodContainer
            get() = Holder.INST
    }

    private object Holder {
        val INST = BotApiMethodContainer()
    }

    fun addBotController(path: String, controller: BotApiMethodController) {
        if (controllerMap.containsKey(path)) throw BotApiMethodContainerException("path $path already add")
        LOGGER.trace("add telegram bot controller for path: $path")
        controllerMap[path] = controller
    }

    fun getBotApiMethodController(path: String): BotApiMethodController? {
        return controllerMap[path]
    }
}
