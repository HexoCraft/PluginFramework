package com.github.hexosse.pluginframework.pluginapi.command;

import com.github.hexosse.pluginframework.pluginapi.command.exception.CommandHandlerException;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
final class CommandHandler implements ICommandHandler {

    @Override
    public void handle(CommandInfo call) throws CommandHandlerException {
        throw new CommandHandlerException("This CommandHandler does not have an appropriate handler registered.");
    }
}
