package info.tduty.telegram2factorauth.telegram

import info.tduty.telegram2factorauth.telegram.BotRequestMethod.*
import info.tduty.telegram2factorauth.telegram.utils.BotController
import info.tduty.telegram2factorauth.telegram.utils.BotRequestMapping
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.BeansException
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.core.Ordered
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import java.lang.reflect.Method
import java.util.HashMap

@Component
class TelegramUpdateHandlerBeanPostProcessor : BeanPostProcessor, Ordered {

    companion object {
        private val LOGGER: Logger = LoggerFactory.getLogger(TelegramUpdateHandlerBeanPostProcessor::class.java)
    }

    private val container: BotApiMethodContainer = BotApiMethodContainer.instanse
    private val botControllerMap: MutableMap<String, Class<*>> = HashMap()

    @Throws(BeansException::class)
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
        val beanClass: Class<*> = bean.javaClass
        if (beanClass.isAnnotationPresent(BotController::class.java)) {
            botControllerMap[beanName] = beanClass
        }
        return bean
    }

    @Throws(BeansException::class)
    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
        val original: Class<*> = botControllerMap[beanName] ?: return bean
        original.methods
                .filter { it.isAnnotationPresent(BotRequestMapping::class.java) }
                .forEach { generateController(bean, it) }
        return bean
    }

    private fun generateController(bean: Any, method: Method) {
        val botController = bean.javaClass.getAnnotation(BotController::class.java)
        val botRequestMapping = method.getAnnotation(BotRequestMapping::class.java)
        val path = botController.value.firstOrNull() ?: "" + botRequestMapping.value.firstOrNull() ?: ""
        val controller = when (botRequestMapping.method.first()) {
            MSG -> createControllerUpdate2ApiMethod(bean, method)
            EDIT -> createProcessListForController(bean, method)
        }
        if (controller != null) container.addBotController(path, controller)
    }

    private fun createControllerUpdate2ApiMethod(bean: Any, method: Method): BotApiMethodController {
        return object : BotApiMethodController(bean, method) {
            override fun successUpdatePredicate(update: Update?): Boolean {
                return update != null && update.hasMessage()
                        && (update.message.hasText() || update.message.hasContact())
            }
        }
    }

    private fun createProcessListForController(bean: Any, method: Method): BotApiMethodController {
        return object : BotApiMethodController(bean, method) {
            override fun successUpdatePredicate(update: Update?): Boolean {
                return update != null && update.hasCallbackQuery() && update.callbackQuery.data != null
            }
        }
    }

    override fun getOrder() = 100
}
