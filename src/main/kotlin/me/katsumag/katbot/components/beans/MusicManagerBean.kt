package me.katsumag.katbot.components.beans

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager
import me.katsumag.katbot.audio.MusicManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

/**
 * Factory class for the [MusicManager] bean method, used for getting an instance
 * of the Guild's [MusicManager] for the specific guild, used for access to the
 * [dev.bombardy.bardybot.audio.TrackScheduler], [com.sedmelluq.discord.lavaplayer.player.AudioPlayer]
 and [dev.bombardy.bardybot.audio.SendHandler]
 */
@Component
class MusicManagerBean {

    @Bean
    @Scope("prototype")
    @Synchronized
    fun musicManager(audioPlayerManager: AudioPlayerManager) = MusicManager(audioPlayerManager)
}