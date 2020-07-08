package me.katsumag.katbot.commands.paste

import com.fasterxml.jackson.databind.ObjectMapper
import dev.bombardy.octo.command.Command
import me.duncte123.botcommons.web.ContentType
import me.duncte123.botcommons.web.WebParserUtils
import me.duncte123.botcommons.web.WebUtils
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import okhttp3.RequestBody
import java.awt.Color
import java.util.function.Consumer


class PasteCommand : Command(listOf("paste"), false) {

    private val HASTE_SERVER = "https://bin.katsumag.me/"

    init {
        WebUtils.setUserAgent("Mozilla/5.0 KatBot#0828 / katsumag#7876")
    }

    override suspend fun execute(message: Message, arguments: List<String>) {

        if (arguments.isEmpty()) {
            message.channel.sendMessage(helpMessage).queue()
            return
        }

        val invoke = "paste"
        val contentRaw = message.contentRaw
        val index = contentRaw.indexOf(invoke) + invoke.length
        val body = contentRaw.substring(index).trim()
        createPaste(body, Consumer<String> { text -> message.channel.sendMessage(text).queue() })
    }

    private fun createPaste(text: String, callback: Consumer<String>) {
        val request = WebUtils.defaultRequest()
                .post(RequestBody.create(null, text.toByteArray()))
                .addHeader("Content-Type", ContentType.TEXT_PLAIN.type)
                .url(HASTE_SERVER + "documents")
                .build()
        WebUtils.ins.prepareRaw(request) { r -> WebParserUtils.toJSONObject(r, ObjectMapper()) }.async(
                { json ->
                    callback.accept(HASTE_SERVER + json.get("key").asText())
                },
                { e -> callback.accept("Error: " + e.message) }
        )
    }

    override val helpMessage = MessageBuilder().setEmbed(EmbedBuilder()
            .setColor(Color.RED)
            .setDescription("Paste Help Message")
            .addField("k!paste <language> <text>", "Uploads the given text to https://bin.katsumag.me/", true)
            .build()).build()

}