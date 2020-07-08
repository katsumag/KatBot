package me.katsumag.katbot.commands.music

import me.katsumag.katbot.getLogger
import me.katsumag.katbot.services.TrackService
import dev.bombardy.octo.command.Command
import net.dv8tion.jda.api.entities.Message

class PauseCommand(private val trackService: TrackService) : Command(listOf("pause"), true) {

    override suspend fun execute(message: Message, arguments: List<String>) {
        val audioPlayer = trackService.getMusicManager(message.textChannel.guild.id).player

        if (!audioPlayer.isPaused) {
            LOGGER.debug("Attempting to pause currently playing track ${audioPlayer.playingTrack}")
            audioPlayer.isPaused = true
            message.channel.sendMessage("**I've put the music on pause...** *for now...*").queue()
        }
    }

    companion object {
        private val LOGGER = getLogger<PauseCommand>()
    }
}