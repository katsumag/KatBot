package me.katsumag.katbot.services

import dev.bombardy.octo.command.CommandManager
import me.katsumag.katbot.commands.colour.HexCommand
import me.katsumag.katbot.commands.colour.RGBCommand
import me.katsumag.katbot.commands.moderation.BanCommand
import me.katsumag.katbot.commands.moderation.KickCommand
import me.katsumag.katbot.commands.moderation.PrefixCommand
import me.katsumag.katbot.commands.mojang.MinecraftCommand
import me.katsumag.katbot.commands.music.*
import me.katsumag.katbot.commands.paste.PasteCommand
import me.katsumag.katbot.commands.reddit.RedditNewCommand
import me.katsumag.katbot.commands.tickets.NewTicketCommand
import me.katsumag.katbot.config.BotConfig
import net.dean.jraw.RedditClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommandService @Autowired constructor(
        commandManager: CommandManager,
        trackService: TrackService,
        botConfig: BotConfig,
        redditClient: RedditClient
) {

    init {
        //music
        commandManager.register(PlayCommand(trackService, commandManager.prefix))
        commandManager.register(PauseCommand(trackService))
        commandManager.register(SkipCommand(trackService))
        commandManager.register(QueueCommand(trackService))
        commandManager.register(LoopCommand(trackService))
        commandManager.register(NowPlayingCommand(trackService))
        commandManager.register(VolumeCommand(trackService))
        commandManager.register(DeleteCommand(trackService))

        //tickets
        commandManager.register(NewTicketCommand())

        //mojang
        commandManager.register(MinecraftCommand())

        //paste
        commandManager.register(PasteCommand())

        //moderation
        commandManager.register(KickCommand())
        commandManager.register(BanCommand())
        commandManager.register(PrefixCommand(botConfig))

        //colours
        commandManager.register(RGBCommand())
        commandManager.register(HexCommand())

        //reddit
        commandManager.register(RedditNewCommand(redditClient))
    }
}