package me.katsumag.katbot.services

import dev.bombardy.octo.command.CommandManager
import me.katsumag.katbot.commands.music.*
import me.katsumag.katbot.commands.tickets.NewTicketCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * Handles command registration. Will do more in the future.
 *
 * @author Callum Seabrook
 * @since 1.0
 */
@Service
class CommandService @Autowired constructor(
        commandManager: CommandManager,
        trackService: TrackService
) {

    init {
        commandManager.register(PlayCommand(trackService, commandManager.prefix))
        commandManager.register(PauseCommand(trackService))
        commandManager.register(SkipCommand(trackService))
        commandManager.register(QueueCommand(trackService))
        commandManager.register(LoopCommand(trackService))
        commandManager.register(NewTicketCommand())
    }
}