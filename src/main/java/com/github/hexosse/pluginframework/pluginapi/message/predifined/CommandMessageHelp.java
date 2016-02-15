package com.github.hexosse.pluginframework.pluginapi.message.predifined;

import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandArgument;
import com.github.hexosse.pluginframework.pluginapi.command.CommandError;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.message.*;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This messgae is used to format command message send to user for help
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CommandMessageHelp extends Message
{
	public CommandMessageHelp(CommandError error, CommandInfo commandInfo)
	{
		PluginCommand<?> command = commandInfo.getCommand();

		// Not enough parameters for the command
		if(error != null && error == CommandError.NOT_ENOUGH_ARGUMENTS)
		{
			this.add(new MessageLine(new MessagePart("")));
			this.add(new MessageLine(new MessagePart(MessageText.commmand_not_enough_parameters).color(MessageColor.ERROR)));
		}
		else if(error != null && error == CommandError.MISMATCH_ARGUMENTS)
		{
			this.add(new MessageLine(new MessagePart("")));
			this.add(new MessageLine(new MessagePart(MessageText.commmand_error).color(MessageColor.ERROR)));
			this.add(new MessageLine(new MessagePart(MessageText.commmand_use_help).color(MessageColor.WARNING)));
		}


		// Lines of the message
		MessageLine line = new MessageLine();
		// - Full command
		line.add(command.help());
		// - Arguments
		for(CommandArgument<?> argument : command.getArguments())
			line.add(argument.help());

		//
		this.add(line);
	}
}
