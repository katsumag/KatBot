package me.katsumag.katbot.services

import me.katsumag.katbot.audio.MusicManager
import me.katsumag.katbot.audio.Result
import me.katsumag.katbot.getBean
import me.katsumag.katbot.getLogger
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.VoiceChannel
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException
import org.springframework.beans.factory.BeanFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Handles connection to a voice channel, used by [TrackService] to join the
 * bot to a voice channel in order to play music.
 *
 * @author Callum Seabrook
 * @since 1.0
 */
@Service
class ConnectionService @Autowired constructor(
        private val jda: JDA,
        private val beanFactory: BeanFactory
) {

    /**
     * Connects the bot to the given voice channel, or, if the bot is
     * already in a channel, does nothing.
     */
    fun join(channel: VoiceChannel) = runCatching {
        channel.guild.audioManager.openAudioConnection(channel)
        LOGGER.debug("Successfully connected to voice channel $channel in guild ${channel.guild}")

        Result.SUCCESSFUL
    }.getOrElse {
        if (it is InsufficientPermissionException) return Result.NO_PERMISSION_TO_JOIN
        Result.OTHER
    }

    /**
     * Disconnects the bot from the voice channel it's currently in, and will stop
     * the currently playing track and clear the queue if [clearQueue] is true, or,
     * if the bot is not in a channel, does nothing.
     *
     * @param clearQueue if the queue should be cleared when the bot leaves the channel
     */
    fun leave(guildId: String, clearQueue: Boolean) {
        val musicManager = beanFactory.getBean<MusicManager>()
        if (clearQueue) {
            musicManager.scheduler.clearQueue()
            musicManager.player.stopTrack()
        }

        requireNotNull(jda.getGuildById(guildId)).audioManager.closeAudioConnection()

        LOGGER.debug("Successfully disconnected from voice channel")
    }

    fun reconnect(guildId: String) {
        val guild = requireNotNull(jda.getGuildById(guildId))
        val voiceChannel = guild.selfMember.voiceState?.channel

        LOGGER.debug("Attempting to disconnect from voice channel $voiceChannel in guild with id $guildId")
        guild.audioManager.closeAudioConnection()

        LOGGER.debug("Attempting to reconnect to voice channel $voiceChannel in guild with id $guildId")
        runCatching {
            guild.audioManager.openAudioConnection(voiceChannel)
            LOGGER.debug("Successfully reconnected to voice channel $voiceChannel in guild with id $guildId")
        }.getOrElse {
            LOGGER.debug("Unable to connect to voice channel $voiceChannel. ${it.message}")
        }
    }

    companion object {
        private val LOGGER = getLogger<ConnectionService>()
    }
}