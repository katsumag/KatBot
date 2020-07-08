package me.katsumag.katbot.components

import com.sedmelluq.discord.lavaplayer.jdaudp.NativeAudioSendFactory
import me.katsumag.katbot.getLogger
import dev.bombardy.octo.command.CommandManager
import me.katsumag.katbot.config.BotConfig
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.requests.GatewayIntent
import net.dv8tion.jda.api.utils.cache.CacheFlag
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

/**
 * Handles set up of [JDA] and [CommandManager], used for connecting
 * to the Discord API (and ultimately, the Discord bot), and handling
 * commands.
 *
 * @author Callum Seabrook
 * @since 1.0
 */
@Component
class JDAComponent {

    @Bean
    fun jda(config: BotConfig) = runCatching {
        JDABuilder.create(config.token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                    .setActivity(Activity.playing("katsumag.me"))
                    .setAudioSendFactory(NativeAudioSendFactory())
                    .disableCache(DISABLED_FLAGS)
                    .build()
    }.getOrElse {
        LOGGER.error("Your bot token is empty or invalid! You can configure your token by providing the -Dbot.token=my_token argument when running this application")
        exitProcess(0)
    }

    @Bean
    fun commandManager(config: BotConfig, jda: JDA) = CommandManager(jda, "k!", COMMAND_MESSAGES)

    companion object {

        private val DISABLED_FLAGS = listOf(
                CacheFlag.ACTIVITY,
                CacheFlag.CLIENT_STATUS,
                CacheFlag.EMOTE
        )

        private val COMMAND_MESSAGES = mapOf(
                "commandNotFound" to "Sorry, I couldn't find the command you were looking for."
        )

        private val LOGGER = getLogger<JDAComponent>()
    }
}