package com.github.hexosse.pluginframework.pluginapi.command;

import com.github.hexosse.pluginframework.pluginapi.command.exception.CommandHandlerException;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
final class CommandHandler implements SubCommandHandler {

    @Override
    public void handle(CommandInfo call) throws CommandHandlerException {
        throw new CommandHandlerException("This SubCommandHandler does not have an appropriate handler registered.");
    }
}
