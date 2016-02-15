package com.github.hexosse.pluginframework.pluginapi.message;

import net.md_5.bungee.api.ChatColor;

import java.util.logging.Level;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class MessageSeverity
{
	public static MessageSeverity INFO = new MessageSeverity(Level.INFO, MessageColor.INFO.color());
	public static MessageSeverity WARNING = new MessageSeverity(Level.WARNING, MessageColor.WARNING.color());
	public static MessageSeverity ERROR = new MessageSeverity(Level.SEVERE, MessageColor.ERROR.color());

    private final Level severity;
    private final ChatColor color;

	//
    public MessageSeverity(Level severity, ChatColor color)
    {
        this.severity = severity;
        this.color = color;
    }

    //
    public Level severity()
    {
        return this.severity;
    }

    //
    public ChatColor color()
    {
        return this.color;
    }
}
