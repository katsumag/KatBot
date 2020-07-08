package me.katsumag.katbot.commands.music

import me.katsumag.katbot.services.TrackService
import dev.bombardy.octo.command.Command
import net.dv8tion.jda.api.entities.Message

class LoopCommand(private val trackService: TrackService) : Command(listOf("loop", "l"), true) {

    override suspend fun execute(message: Message, arguments: List<String>) {
        val channel = message.textChannel
        val scheduler = trackService.getMusicManager(message.textChannel.guild.idLong).scheduler

        when (scheduler.isLooping) {
            false -> channel.sendMessage("**I've enabled looping! I will now loop the current track for all eternity!** *Yay...*").queue()
            else -> channel.sendMessage("**I've disabled looping! I will no longer loop the current track!** *Finally...*").queue()
        }

        scheduler.isLooping = !scheduler.isLooping
    }
}