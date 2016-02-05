package com.github.hexosse.pluginframework.pluginapi.command;

import com.github.hexosse.pluginframework.pluginapi.command.exception.CommandHandlerException;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */

public interface ICommandHandler {
    /**
     * Handle a call from a Command.
     *
     * <p>The passed in call has a number of properties, including the Player
     * (if applicable), CommandSender, information on the Command, and
     * any additional arguments passed.
     *
     * <p>If the handler throws a {@link CommandHandlerException}, then the failure's message is
     * printed to the command sender.
     *
     * @param call Contains all the information about the call.
     * @throws CommandHandlerException An optional error trap mechanism to display errors.
     */
    public void handle(CommandInfo call) throws CommandHandlerException;
}

