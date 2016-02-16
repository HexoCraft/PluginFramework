package com.github.hexosse.pluginframework.pluginapi.message;

import net.md_5.bungee.api.ChatColor;

/**
 * List of colors used in Messages
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class MessageColor
{
	public static MessageColor TEXT = new MessageColor(ChatColor.WHITE);

	// Colors used with commands
	public static MessageColor COMMAND = new MessageColor(ChatColor.AQUA);
	public static MessageColor SUBCOMMAND = new MessageColor(ChatColor.AQUA);
	public static MessageColor ARGUMENT_MANDATORY = new MessageColor(ChatColor.DARK_GREEN);
	public static MessageColor ARGUMENT_OPTIONAL = new MessageColor(ChatColor.DARK_AQUA);
	public static MessageColor DESCRIPTION = new MessageColor(ChatColor.YELLOW);

	// Colors used with predifined severity
	public static MessageColor INFO = new MessageColor(ChatColor.WHITE);
	public static MessageColor WARNING = new MessageColor(ChatColor.GOLD);
	public static MessageColor ERROR = new MessageColor(ChatColor.RED);

	private ChatColor color;

	//
	public MessageColor(ChatColor color) { this.color = color; }

	//
	public MessageColor(MessageSeverity severity) { this.color = severity.color(); }

	//
	public ChatColor color()
	{
		return this.color;
	}

	//
	public String toString()
	{
		return this.color.toString();
	}
}
