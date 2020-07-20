package me.katsumag.katbot.commands.music

import dev.bombardy.octo.command.Command
import me.katsumag.katbot.extensions.randomColour
import me.katsumag.katbot.format
import me.katsumag.katbot.formatName
import me.katsumag.katbot.getLogger
import me.katsumag.katbot.services.TrackService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.Message
import kotlin.time.ExperimentalTime
import kotlin.time.milliseconds

class NowPlayingCommand(private val trackService: TrackService) : Command(listOf("nowplaying", "np"), true) {

    @ExperimentalTime
    override suspend fun execute(message: Message, arguments: List<String>) {
        val channel = message.textChannel
        val musicManager = trackService.getMusicManager(message.guild.id)

        val nowPlaying = musicManager.player.playingTrack
                ?: return channel.sendMessage("**I'm not playing anything at the moment! Use the play command to get the party started!**").queue()
        val requester = nowPlaying.userData as? Member
                ?: return LOGGER.error("User data for requested track $nowPlaying should have been of type Member and wasn't, please report to creator.")

        val position = nowPlaying.position.milliseconds.format()
        val duration = nowPlaying.duration.milliseconds.format()

        val percentage = (nowPlaying.position.toDouble() / nowPlaying.duration)

        val embed = EmbedBuilder()
                .setAuthor("What I'm playing now", "https://bot.katsumag.me", "https://cdn.katsumag.me/KatBot.png")
                .setThumbnail("https://img.youtube.com/vi/${nowPlaying.identifier}/maxresdefault.jpg")
                .setDescription("""
                    [${nowPlaying.info.title}](${nowPlaying.info.uri})
                    
                    `${calculateBar(percentage)}`
                    
                    `$position / $duration`
                    
                    *Requested by: ${requester.formatName()}*
                """.trimIndent())
                .setColor(randomColour())
                .build()

        channel.sendMessage(embed).queue()
    }

    private fun calculateBar(percentage: Double): String {
        val position = (percentage * BAR_LENGTH).toInt()
        val output = StringBuilder()

        repeat(BAR_LENGTH) {
            when (it) {
                position -> output.append(DOT)
                else -> output.append(DASH)
            }
        }

        return output.toString()
    }

    companion object {
        const val DOT = "\uD83D\uDD18"
        const val DASH = "\u25AC"
        const val BAR_LENGTH = 31

        private val LOGGER = getLogger<NowPlayingCommand>()
    }
}