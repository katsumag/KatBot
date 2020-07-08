package me.katsumag.katbot.commands.tickets

import dev.bombardy.octo.command.Command
import net.dv8tion.jda.api.entities.Message

class NewTicketCommand : Command(listOf("ticket", "t"), false){

    override suspend fun execute(message: Message, arguments: List<String>) {

        message.channel.sendMessage("I LIKE CHILDREN").queue()

    }

}