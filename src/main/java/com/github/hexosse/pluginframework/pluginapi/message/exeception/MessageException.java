package com.github.hexosse.pluginframework.pluginapi.message.exeception;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class MessageException extends RuntimeException{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MessageException(String message)
    {
        super(message);
    }

    public MessageException(Throwable cause)
    {
        super(cause);
    }

    public MessageException(String message, Throwable cause)
    {
        super(message, cause);
    }

}
