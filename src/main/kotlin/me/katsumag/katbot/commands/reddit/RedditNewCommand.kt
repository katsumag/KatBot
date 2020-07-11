package me.katsumag.katbot.commands.reddit

import dev.bombardy.octo.command.Command
import me.katsumag.katbot.extensions.await
import me.katsumag.katbot.extensions.randomColour
import net.dean.jraw.RedditClient
import net.dean.jraw.models.SubredditSort
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import java.awt.Color

class RedditNewCommand(private val client: RedditClient) : Command(listOf("redditnew"), false) {

    override suspend fun execute(message: Message, arguments: List<String>) {

        val accumulate = client.subreddit(arguments[0])
                .posts()
                .sorting(SubredditSort.NEW)
                .limit(5)
                .build()
                .accumulate(1)

        val embedBuilder = EmbedBuilder()
                .setColor(randomColour())
                .setDescription("Fetched the 5 newest posts in r/${arguments[0]}")
                .setFooter("Requested by ${message.author.name}")

        accumulate.flatten().take(5).forEach {
            embedBuilder.addField(it.title, it.url, false)
        }

        message.channel.sendMessage(MessageBuilder(embedBuilder.build()).build()).await()

    }

    override val helpMessage = MessageBuilder().setEmbed(EmbedBuilder()
            .setColor(Color.RED)
            .setDescription("Redditnew Help Message")
            .addField("k!redditnew <subreddit>", "Fetches the 5 most recent post of the sub you specify", true)
            .build()).build()

}