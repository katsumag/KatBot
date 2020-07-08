package me.katsumag.katbot.commands.music

import dev.bombardy.octo.command.Command
import me.katsumag.katbot.services.TrackService
import net.dv8tion.jda.api.entities.Message

class VolumeCommand(private val trackService: TrackService) : Command(listOf("volume", "vol")) {

    override suspend fun execute(message: Message, arguments: List<String>) {
        val audioPlayer = trackService.getMusicManager(message.guild.id).player
        val volume = arguments[0].toInt()

        audioPlayer.volume = volume
        message.channel.sendMessage("**I've set the volume to $volume.**").queue()
    }
}