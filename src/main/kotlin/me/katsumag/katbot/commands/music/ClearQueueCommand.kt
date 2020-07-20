package me.katsumag.katbot.commands.music

import dev.bombardy.octo.command.Command
import me.katsumag.katbot.extensions.await
import me.katsumag.katbot.extensions.isNull
import me.katsumag.katbot.services.TrackService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

class ClearQueueCommand(private val trackService: TrackService): Command(listOf("clear"), true){

    override suspend fun execute(message: Message, arguments: List<String>) {

        if (isNull(message.member)) {
            message.channel.sendMessage("Could not get a member for this message!").await()
            return
        }

        if (! message.member!!.hasPermission(Permission.ADMINISTRATOR)) {
            message.channel.sendMessage("You don't have permission to execute this command").await()
            return
        }

        val manager = trackService.getMusicManager(message.guild.id)
        manager.isPaused = true
        manager.scheduler.queue.clear()

        message.channel.sendMessage("Queue cleared!").await()

    }

    override val helpMessage = MessageBuilder().setEmbed(EmbedBuilder()
            .setColor(Color.RED)
            .setDescription("Remove Track From Queue Help Message")
            .addField("k!clear", "Clears the queue.", true)
            .build()).build()
}