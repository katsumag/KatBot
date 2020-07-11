package me.katsumag.katbot.commands.moderation

import dev.bombardy.octo.command.Command
import me.katsumag.katbot.config.BotConfig
import me.katsumag.katbot.extensions.await
import me.katsumag.katbot.extensions.sizeEquals
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

class PrefixCommand(private val config: BotConfig) : Command(listOf("prefix"), false) {

    override suspend fun execute(message: Message, arguments: List<String>) {

        if (! arguments.sizeEquals(1)) {
            message.channel.sendMessage(helpMessage).await()
            return
        }

        if (message.member == null) {
            message.channel.sendMessage("No valid member found, cannot check permissions!").await()
            return
        }

        if (message.member!!.hasPermission(Permission.ADMINISTRATOR)) {
            config.prefix = arguments[0]
            message.channel.sendMessage("Prefix changed!").await()
        }

    }

    override val helpMessage = MessageBuilder().setEmbed(EmbedBuilder()
            .setColor(Color.RED)
            .setDescription("Prefix Help Message")
            .addField("k!prefix <prefix>", "Change my prefix", true)
            .build()).build()

}