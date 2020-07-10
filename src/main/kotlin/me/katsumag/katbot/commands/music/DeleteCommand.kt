package me.katsumag.katbot.commands.music

import dev.bombardy.octo.command.Command
import me.katsumag.katbot.extensions.await
import me.katsumag.katbot.extensions.sizeEquals
import me.katsumag.katbot.services.TrackService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

class DeleteCommand(private val service: TrackService) : Command(listOf("delete"), false){

    override suspend fun execute(message: Message, arguments: List<String>) {

        if (arguments.sizeEquals(1)) {
            val queue = service.getMusicManager(message.textChannel.guild.id).scheduler.queue
            queue.remove(arguments[0].toIntOrNull()?.let { queue.elementAtOrNull(it - 1) })
            message.channel.sendMessage("Song removed from queue!").await()
        } else message.channel.sendMessage(helpMessage).await()
    }

    override val helpMessage = MessageBuilder().setEmbed(EmbedBuilder()
            .setColor(Color.RED)
            .setDescription("Remove Track From Queue Help Message")
            .addField("k!delete <index>", "Removes the song at the specified index from the queue, unless the index doesn't exist, then it will be ignored.", true)
            .build()).build()

}