package me.katsumag.katbot.commands.colour

import dev.bombardy.octo.command.Command
import me.katsumag.katbot.extensions.await
import me.katsumag.katbot.extensions.nextHexValue
import me.katsumag.katbot.extensions.toHexString
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import kotlin.random.Random


class HexCommand : Command(listOf("hex"), true){
    override suspend fun execute(message: Message, arguments: List<String>) {

        if (arguments.size > 1) {
            message.channel.sendMessage(helpMessage).await()
            return
        }

        val image = BufferedImage(1024, 128, BufferedImage.TYPE_INT_ARGB)
        val graphics2D = image.createGraphics()
        val colour = Color.decode(if (arguments.isEmpty()) Random.nextHexValue() else arguments[0])
        graphics2D.color = colour
        graphics2D.fillRect(0, 0, 1024, 128)
        val byteArrayOutputStream = ByteArrayOutputStream()
        byteArrayOutputStream.use {
            ImageIO.write(image, "png", it)
            ByteArrayInputStream(it.toByteArray()).use {
                message.channel.sendFile(it, "hex.png").embed(
                        EmbedBuilder()
                                .setColor(colour)
                                .setImage("attachment://hex.png")
                                .setDescription("Your Colour")
                                .setFooter("RGB value: ${colour.red}, ${colour.green}, ${colour.blue}. Hex value: ${colour.toHexString()}. Requested by ${message.author.name}")
                                .build()
                ).await()
            }
        }
    }

    override val helpMessage = MessageBuilder().setEmbed(EmbedBuilder()
            .setColor(Color.RED)
            .setDescription("Hex Help Message")
            .addField("k!hex <hex code> (parameter optional)", "Generates an embed with the colour specifed. If none is specifed, I choose a random colour", true)
            .build()).build()

}