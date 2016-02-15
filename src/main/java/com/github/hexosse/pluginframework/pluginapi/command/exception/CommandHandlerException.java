package com.github.hexosse.pluginframework.pluginapi.command.exception;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */

public class CommandHandlerException extends Exception
{
    private static final long serialVersionUID = 4940560265978818401L;

    public CommandHandlerException(String message)
    {
        super(message);
    }
}

