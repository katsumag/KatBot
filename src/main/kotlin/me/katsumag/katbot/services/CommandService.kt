package me.katsumag.katbot.services

import dev.bombardy.octo.command.CommandManager
import me.katsumag.katbot.commands.moderation.BanCommand
import me.katsumag.katbot.commands.moderation.KickCommand
import me.katsumag.katbot.commands.mojang.MinecraftCommand
import me.katsumag.katbot.commands.music.*
import me.katsumag.katbot.commands.paste.PasteCommand
import me.katsumag.katbot.commands.tickets.NewTicketCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CommandService @Autowired constructor(
        commandManager: CommandManager,
        trackService: TrackService
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
    }
}