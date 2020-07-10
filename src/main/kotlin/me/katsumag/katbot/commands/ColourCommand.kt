package me.katsumag.katbot.commands

import dev.bombardy.octo.command.Command
import net.dv8tion.jda.api.entities.Message

class ColourCommand : Command(listOf("colour")){
    override suspend fun execute(message: Message, arguments: List<String>) {
        TODO("Not yet implemented")
    }
}