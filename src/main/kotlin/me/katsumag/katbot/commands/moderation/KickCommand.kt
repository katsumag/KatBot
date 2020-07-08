package me.katsumag.katbot.commands.moderation

import dev.bombardy.octo.command.Command
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Message
import java.awt.Color
import java.util.function.Consumer

class KickCommand : Command(listOf("kick"), false){

    override suspend fun execute(message: Message, arguments: List<String>) {

        if (arguments.size != 2 || message.mentionedMembers.isEmpty()) {
            message.channel.sendMessage(helpMessage).queue()
            return
        }

        if(! message.member?.canInteract(message.mentionedMembers[0])!! || ! message.member!!.hasPermission(Permission.KICK_MEMBERS)) {
            message.channel.sendMessage("You don't have permission to perform this command").queue()
            return
        }

        if(! message.guild.selfMember.canInteract(message.mentionedMembers[0]) || ! message.guild.selfMember.hasPermission(Permission.KICK_MEMBERS)) {
            message.channel.sendMessage("I don't have permission to perform this command").queue()
            return
        }

        val reason = arguments.subList(1, arguments.size).joinToString()
        message.guild.kick(message.mentionedMembers[0], reason).reason(reason).queue { message.channel.sendMessage("Kicked ${message.mentionedMembers[0].nickname}").queue() }

    }

    override val helpMessage = MessageBuilder().setEmbed(EmbedBuilder()
            .setColor(Color.RED)
            .setDescription("Kick Help Message")
            .addField("k!kick <mention> <reason>", "Kick that user, sending them a message with the reason", true)
            .build()).build()

}