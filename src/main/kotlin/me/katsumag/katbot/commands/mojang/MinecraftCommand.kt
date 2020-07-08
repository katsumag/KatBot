package me.katsumag.katbot.commands.mojang

import com.github.natanbc.reliqua.util.StatusCodeValidator
import dev.bombardy.octo.command.Command
import me.duncte123.botcommons.web.WebUtils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import java.awt.Color
import java.util.function.Consumer

class MinecraftCommand : Command(listOf("mcname"), false){

    override suspend fun execute(message: Message, arguments: List<String>) {

        if (arguments.size != 2) {
            message.channel.sendMessage(helpMessage).queue()
            return
        }

        val item = arguments[0]
        val id = arguments[1]

       when(item.toLowerCase()) {

           "uuid" -> {
               //fetch uuid
                fetchUUID(id, Consumer { uuid ->
                    run {
                        if (uuid.isEmpty()) {
                            message.channel.sendMessage("User with name $id was not found").queue()
                            return@Consumer
                        }

                        message.channel.sendMessage("$id's UUID is $uuid").queue()

                    }
                })
           }

           "names" -> {
               //fetch names
               fetchNameHistory(id, Consumer { names ->
                   run {
                       if (names.isEmpty()) {
                           message.channel.sendMessage("UUID $id is not valid").queue()
                           return@Consumer
                       }

                       message.channel.sendMessage("Name history for $id:\n${names.joinToString { "$it, " }}").queue()

                   }
               })

           }

           else -> message.channel.sendMessage(helpMessage).queue()

       }

    }

    private fun fetchUUID(username: String, callback: Consumer<String>) {
        WebUtils.ins.getJSONObject(
                "https://api.mojang.com/users/profiles/minecraft/$username"
        ) { builder ->  builder.setStatusCodeValidator(StatusCodeValidator.ACCEPT_200)}.async({
            callback.accept(it.get("id").asText())
        }, {
            callback.accept("")
        })
    }

    private fun fetchNameHistory(uuid: String, callback: Consumer<List<String>>) {
        WebUtils.ins.getJSONArray(
                "https://api.mojang.com/user/profiles/$uuid/names"
        ) { builder ->  builder.setStatusCodeValidator(StatusCodeValidator.ACCEPT_200)}.async({ it ->
            val names = mutableListOf<String>()
            it.forEach { names.add(it.get("name").asText()) }
            callback.accept(names)
        }, {
            callback.accept(listOf())
        })
    }

    override val helpMessage = MessageBuilder().setEmbed(EmbedBuilder()
            .setColor(Color.RED)
            .setDescription("McName/McUUID Help Message")
            .addField("k!mcname names <player>", "Get the name history of a player", true)
            .addField("k!mcname uuid <player>", "Get the UUID of a player", true)
            .build()).build()
}