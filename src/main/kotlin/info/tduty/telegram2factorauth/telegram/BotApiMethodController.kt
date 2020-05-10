package info.tduty.telegram2factorauth.telegram

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.telegram.telegrambots.meta.api.methods.BotApiMethod
import org.telegram.telegrambots.meta.api.objects.Update
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

abstract class BotApiMethodController(
        private val bean: Any,
        private val method: Method
) {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(BotApiMethodController::class.java)
    }

    private val processUpdate: (Update) -> List<BotApiMethod<*>> =
            if (typeListReturnDetect()) { update -> processList(update) }
            else { update -> processSingle(update) }

    abstract fun successUpdatePredicate(update: Update?): Boolean

    fun process(update: Update): List<BotApiMethod<*>> {
        if (!successUpdatePredicate(update)) return emptyList()
        try {
            return processUpdate.invoke(update)
        } catch (e: IllegalAccessException) {
            LOGGER.error("bad invoke method", e)
        } catch (e: InvocationTargetException) {
            LOGGER.error("bad invoke method", e)
        }
        return emptyList()
    }

    fun typeListReturnDetect(): Boolean {
        return MutableList::class.java == method.returnType
                || List::class.java == method.returnType
    }

    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    private fun processSingle(update: Update): List<BotApiMethod<*>> {
        val botApiMethod: BotApiMethod<*>? = method.invoke(bean, update) as? BotApiMethod<*>
        return if (botApiMethod != null) listOf(botApiMethod) else emptyList()
    }

    @Throws(InvocationTargetException::class, IllegalAccessException::class)
    private fun processList(update: Update): List<BotApiMethod<*>> {
        return (method.invoke(bean, update) as? List<BotApiMethod<*>>) ?: emptyList()
    }
}
