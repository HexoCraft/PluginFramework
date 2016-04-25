package com.github.hexosse.pluginframework.pluginapi.command.predifined;

/*
 * Copyright 2016 Hexosse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.hexosse.pluginframework.pluginapi.Plugin;
import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandArgument;
import com.github.hexosse.pluginframework.pluginapi.command.CommandError;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.command.type.ArgTypeInteger;
import com.github.hexosse.pluginframework.pluginapi.message.*;
import com.github.hexosse.pluginframework.pluginapi.message.predifined.Help;
import com.google.common.collect.Lists;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.bukkit.util.ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH;
import static org.bukkit.util.ChatPaginator.CLOSED_CHAT_PAGE_HEIGHT;
import static org.bukkit.util.ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CommandHelp<PluginClass extends Plugin> extends PluginCommand<PluginClass>
{
	static String HELP = "help";

	public CommandHelp(PluginClass plugin)
	{
		super(HELP, plugin);
		this.setAliases(Lists.newArrayList(HELP, "h", "?", "aide"));
		this.addArgument(new CommandArgument<Integer>("page", ArgTypeInteger.get(), 1, false, false, "Page number"));
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
		//List<Data> datas = Lists.newArrayList();
		HelpLines helpLines = new HelpLines(CLOSED_CHAT_PAGE_HEIGHT-1);

		// Command
		PluginCommand<?> command = commandInfo.getCommand();

		// Parent command
		PluginCommand<?> parentCommand = command.getParentCommand();

		// Main command
		PluginCommand<?> mainCommand = command.getName().toLowerCase().equals(HELP.toLowerCase()) ? parentCommand : command;
		if(mainCommand.getMaxArgs() > 0)
		{
			CommandInfo mainCommandInfo = new CommandInfo(commandInfo.getSender(), mainCommand, mainCommand.getName(), new String[0], null);
			Help mainHelp = new Help(mainCommandInfo);

			if(command.getPermission()==null)
				helpLines.add(new HelpLine(mainCommandInfo, mainHelp));
			else if(command.getPermission()!=null && command.getPermission().isEmpty()==false && commandInfo.getSender().hasPermission(command.getPermission())==true)
				helpLines.add(new HelpLine(mainCommandInfo, mainHelp));
		}

		// Sub command
		for(Map.Entry<String,PluginCommand<?>> entry : mainCommand.getSubCommands().entrySet())
		{
			PluginCommand<?> subCommand = entry.getValue();

			CommandInfo subCommandInfo = new CommandInfo(commandInfo.getSender(), subCommand, subCommand.getName(), new String[0], null);
			Help subHelp = new Help(subCommandInfo);

			if(subCommandInfo.getCommand().getPermission()==null || subCommandInfo.getCommand().getPermission().isEmpty()==true)
				helpLines.add(new HelpLine(subCommandInfo, subHelp));
			else if(subCommandInfo.getCommand().getPermission().isEmpty()==false && commandInfo.getSender().hasPermission(subCommandInfo.getCommand().getPermission())==true)
				helpLines.add(new HelpLine(subCommandInfo, subHelp));
		}

		// Page requested
		int page = Integer.parseInt(commandInfo.hasNamedArg("page")==true?commandInfo.getNamedArg("page"):"1");
		int maxPages = helpLines.currentPage;

		// Check page number
		page = ((page>=maxPages) ? maxPages : ((page<=0) ? 1 : page));

		// Title line
		MessagePart prev = getPrev(command, page - 1, maxPages);
		MessagePart help = new MessagePart(" " + MessageText.help_for_command + " \"" + parentCommand.getName() + "\" ").color(MessageColor.DESCRIPTION);
		MessagePart index = getIndex(command, page, maxPages);
		MessagePart next = getNext(command, page + 1, maxPages);
		int len = (prev.getText() +  help.getText() + index.getText() + next.getText()).length();
		MessagePart dash = new MessagePart(ChatColor.STRIKETHROUGH + StringUtils.leftPad("", (GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH-len)/2, "-")).color(MessageColor.DESCRIPTION);

		Message m = new Message(new MessageLine().add(dash).add(prev).add(help).add(index).add(next).add(dash));
		plugin.messageManager.send(commandInfo,new Message(""));
		plugin.messageManager.send(commandInfo,m);

		// Help lines
		for(HelpLine line : helpLines.lines)
		{
			if(line.page==page)
				plugin.messageManager.send(line.commandInfo, line.message);
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
		String helpCommand = "";
		while(command.getParentCommand()!=null)
		{
			command = command.getParentCommand();
			helpCommand = command.getName() + " " + helpCommand;
		}
		helpCommand = "/" + helpCommand + " help " + Integer.toString(pageNumber);

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
		String helpCommand = "";
		while(command.getParentCommand()!=null)
		{
			command = command.getParentCommand();
			helpCommand = command.getName() + " " + helpCommand;
		}
		helpCommand = "/" + helpCommand + " help " + Integer.toString(pageNumber);


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


	class HelpLine
	{
		public CommandInfo commandInfo;
		public Help message;
		public int lines;
		public int page;

		public HelpLine(CommandInfo commandInfo, Help message)
		{
			this.commandInfo = commandInfo;
			this.message = message;
			this.lines = getLines(message);
		}

		private int getLines(Help message)
		{
			List<String> lines = new ArrayList<String>();
			for(MessageLine mLine : message.getLines())
				lines.add(mLine.toString());
			return ChatPaginator.paginate(StringUtils.join(lines, ' '), 1, AVERAGE_CHAT_PAGE_WIDTH, CLOSED_CHAT_PAGE_HEIGHT).getLines().length;
		}
	}

	class HelpLines
	{
		List<HelpLine> lines = new ArrayList<>();
		int nbLinePerPage;
		int currentPage;
		int currentPageLines;

		public HelpLines(int nbLinePerPage)
		{
			this.nbLinePerPage = nbLinePerPage;
			this.currentPage = 1;
			this.currentPageLines = 0;
		}

		public void add(HelpLine line)
		{
			// Check if the number of line feet in the current page
			if(this.currentPageLines + line.lines > this.nbLinePerPage)
			{
				this.currentPage++;
				this.currentPageLines = 0;
			}

			// Update the line. It will know it page number
			this.currentPageLines += line.lines;
			line.page = this.currentPage;

			// Add line to the list
			this.lines.add(line);
		}
	}
}
