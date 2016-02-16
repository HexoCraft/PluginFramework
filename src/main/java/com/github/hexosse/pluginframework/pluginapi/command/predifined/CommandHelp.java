package com.github.hexosse.pluginframework.pluginapi.command.predifined;

/*
 * Copyright 2016 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.hexosse.pluginframework.pluginapi.Plugin;
import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandArgument;
import com.github.hexosse.pluginframework.pluginapi.command.CommandError;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.command.type.ArgTypeInteger;
import com.github.hexosse.pluginframework.pluginapi.message.*;
import com.github.hexosse.pluginframework.pluginapi.message.predifined.CommandMessageHelp;
import com.google.common.collect.Lists;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import java.util.List;
import java.util.Map;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CommandHelp<PluginClass extends Plugin> extends PluginCommand<PluginClass>
{
	public CommandHelp(PluginClass plugin)
	{
		super("help", plugin);
		this.setAliases(Lists.newArrayList("help", "h", "?", "aide"));
		this.addArgument(new CommandArgument<Integer>("page", ArgTypeInteger.get(), 1, true, true, "Page number"));
	}

	/**
	 * Executes the given command, returning its success
	 *
	 * @param commandInfo Info about the command
	 *
	 * @return true if a valid command, otherwise false
	 */
	@Override
	public boolean onCommand(CommandInfo commandInfo)
	{
		class data
		{
			public CommandInfo commandInfo;
			public CommandMessageHelp message;

			public data(CommandInfo commandInfo, CommandMessageHelp message)
			{
				this.commandInfo = commandInfo;
				this.message = message;
			}
		}

		List<data> datas = Lists.newArrayList();
		int page = Integer.parseInt(commandInfo.getNamedArg("page"));
		int maxLines = commandInfo.getPlayer()!=null ? 10 : 100;

		// Main command
		PluginCommand<?> mainCommand = commandInfo.getCommand().getMainCommand();
		if(mainCommand.getMaxArgs() > 0)
		{
			CommandInfo mainCommandInfo = new CommandInfo(commandInfo.getSender(), mainCommand, mainCommand.getName(), new String[0], null);
			CommandMessageHelp mainHelp = new CommandMessageHelp(null, mainCommandInfo);

			if(mainCommandInfo.getCommand().getPermission()==null)
				datas.add(new data(mainCommandInfo, mainHelp));
			else if(mainCommandInfo.getCommand().getPermission()!=null && mainCommandInfo.getCommand().getPermission().isEmpty()==false && commandInfo.getSender().hasPermission(mainCommandInfo.getCommand().getPermission())==true)
				datas.add(new data(mainCommandInfo, mainHelp));
		}

		// Sub command
		for(Map.Entry<String,PluginCommand<?>> entry : mainCommand.getSubCommands().entrySet())
		{
			PluginCommand<?> subCommand = entry.getValue();

			CommandInfo subCommandInfo = new CommandInfo(commandInfo.getSender(), subCommand, subCommand.getName(), new String[0], null);
			CommandMessageHelp subHelp = new CommandMessageHelp(null, subCommandInfo);

			if(subCommandInfo.getCommand().getPermission()==null)
				datas.add(new data(subCommandInfo, subHelp));
			else if(subCommandInfo.getCommand().getPermission().isEmpty()==false && commandInfo.getSender().hasPermission(subCommandInfo.getCommand().getPermission())==true)
				datas.add(new data(subCommandInfo, subHelp));
		}

		// Total number of lines
		int nbLines = datas.size() + 1;
		int maxPages = (int)Math.ceil((double)nbLines/(double)maxLines);

		// Check page number
		page = (page<=0) ? 1 : ((page>=maxPages) ? maxPages : page);

		// Title line
		MessagePart prev = getPrev(mainCommand, page - 1, maxPages);
		MessagePart help = new MessagePart(" " + MessageText.help_for_command + " \"" + commandInfo.getCommand().getParentCommand().getName() + "\" ").color(MessageColor.DESCRIPTION);
		MessagePart index = getIndex(mainCommand, page, maxPages);
		MessagePart next = getNext(mainCommand, page + 1, maxPages);
		int len = (prev.getText() +  help.getText() + index.getText() + next.getText()).length();
		MessagePart dash = new MessagePart(ChatColor.STRIKETHROUGH + StringUtils.leftPad("", (51-len)/2, "-")).color(MessageColor.DESCRIPTION);

		Message m = new Message(new MessageLine().add(dash).add(prev).add(help).add(index).add(next).add(dash));
		plugin.messageManager.send(commandInfo,new Message(""));
		plugin.messageManager.send(commandInfo,m);

		// Help lines
		for(int i = (page-1)*maxLines; i < (page)*maxLines; i++)
		{
			if(i<datas.size())
			{
				data d = datas.get(i);
				plugin.messageManager.send(d.commandInfo, d.message);
			}
		}

		return true;
	}

	@Override
	public void onCommandHelp(CommandError error, CommandInfo commandInfo)
	{
	}

	private MessagePart getPrev(PluginCommand<?> command, int pageNumber, int totalPage)
	{
		if(totalPage <= 1 || pageNumber <= 0)
			return new MessagePart("");

		MessagePart prev = new MessagePart(" [<] ");

		// Command
		String helpCommand = "/" + command.getName() + " help " + Integer.toString(pageNumber);

		ComponentBuilder prevHoverText = new ComponentBuilder("");
		prevHoverText.append(MessageText.help_page + " " + Integer.toString(pageNumber)).color(MessageColor.SUBCOMMAND.color());
		ClickEvent prevClickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, helpCommand);
		HoverEvent prevHoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, prevHoverText.create());

		return prev.color(MessageColor.COMMAND).event(prevClickEvent).event(prevHoverEvent);
	}

	private MessagePart getNext(PluginCommand<?> command, int pageNumber, int totalPage)
	{
		if(totalPage <= 1 || pageNumber <= 0 || pageNumber > totalPage)
			return new MessagePart("");

		MessagePart next = new MessagePart(" [>] ");

		// Command
		String helpCommand = "/" + command.getName() + " help " + Integer.toString(pageNumber);

		ComponentBuilder nextHoverText = new ComponentBuilder("");
		nextHoverText.append(MessageText.help_page + " " + Integer.toString(pageNumber)).color(MessageColor.SUBCOMMAND.color());
		ClickEvent nextClickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, helpCommand);
		HoverEvent nextHoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, nextHoverText.create());

		return next.color(MessageColor.COMMAND).event(nextClickEvent).event(nextHoverEvent);
	}

	private MessagePart getIndex(PluginCommand<?> command, int pageNumber, int totalPage)
	{
		if(totalPage <= 1 || pageNumber <= 0 || pageNumber > totalPage)
			return new MessagePart("");

		MessagePart index = new MessagePart(" (" + Integer.toString(pageNumber) + "/" + Integer.toString(totalPage) + ") ");

		return index.color(MessageColor.DESCRIPTION);
	}
}
