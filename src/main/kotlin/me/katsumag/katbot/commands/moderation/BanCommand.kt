package me.katsumag.katbot.commands.moderation

import dev.bombardy.octo.command.Command
import me.katsumag.katbot.extensions.noMentionedMembers
import me.katsumag.katbot.extensions.sizeEquals
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

class BanCommand : Command(listOf("ban")) {

    override suspend fun execute(message: Message, arguments: List<String>) {

        if (! arguments.sizeEquals(3)|| message.noMentionedMembers()) {
            message.channel.sendMessage(helpMessage).queue()
            return
        }

        if(! message.member?.canInteract(message.mentionedMembers[0])!! || ! message.member!!.hasPermission(Permission.BAN_MEMBERS)) {
            message.channel.sendMessage("You don't have permission to perform this command").queue()
            return
        }

        if(! message.guild.selfMember.canInteract(message.mentionedMembers[0]) || ! message.guild.selfMember.hasPermission(Permission.BAN_MEMBERS)) {
            message.channel.sendMessage("I don't have permission to perform this command").queue()
            return
        }

        val reason = arguments.subList(2, arguments.size).joinToString()
        message.guild.ban(message.mentionedMembers[0], arguments[1].toInt()).reason(reason).queue { message.channel.sendMessage("Banned ${message.mentionedMembers[0].nickname}").queue() }

    }

    override val helpMessage = MessageBuilder().setEmbed(EmbedBuilder()
            .setColor(Color.RED)
            .setDescription("Ban Help Message")
            .addField("k!Ban <mention> <duration> <reason>", "Ban that user for the duration specified, sending them a message with the reason", true)
            .build()).build()

}