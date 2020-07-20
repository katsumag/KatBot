package me.katsumag.katbot.commands.colour

import dev.bombardy.octo.command.Command
import me.katsumag.katbot.extensions.await
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

class RGBCommand : Command(listOf("rgb"), true){
    override suspend fun execute(message: Message, arguments: List<String>) {

        if (arguments.size > 3) {
            message.channel.sendMessage(helpMessage).await()
            return
        }

        var r = 0
        var g = 0
        var b = 0

        if (arguments.isEmpty()) {
            r = Random.nextInt(255)
            g = Random.nextInt(255)
            b = Random.nextInt(255)
        } else {
            r = arguments[0].toInt()
            g = arguments[1].toInt()
            b = arguments[2].toInt()
        }

        val image = BufferedImage(1024, 128, BufferedImage.TYPE_INT_ARGB)
        val graphics2D = image.createGraphics()
        val colour = Color(r, g, b)
        graphics2D.color = colour
        graphics2D.drawString("your text here", 0, 0)
        graphics2D.fillRect(0, 0, 1024, 128)
        val byteArrayOutputStream = ByteArrayOutputStream()
        byteArrayOutputStream.use {
            ImageIO.write(image, "png", it)
            ByteArrayInputStream(it.toByteArray()).use {
                message.channel.sendFile(it, "rgb.png").embed(
                    EmbedBuilder()
                            .setColor(colour)
                            .setImage("attachment://rgb.png")
                            .setDescription("Your Colour")
                            .setFooter("RGB value: $r, $g, $b. Hex Value: ${colour.toHexString()}. Requested by ${message.author.name}")
                            .build()
                ).await()
            }
        }
    }

    override val helpMessage = MessageBuilder().setEmbed(EmbedBuilder()
            .setColor(Color.RED)
            .setDescription("Colour Help Message")
            .addField("k!rgb r g b (all parameters optional)", "Generates an embed with the colour specifed. If none is specifed, I choose a random colour", true)
            .build()).build()

}