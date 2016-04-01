package com.github.hexosse.pluginframework.pluginapi;

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

import com.github.hexosse.pluginframework.pluginapi.command.CommandArgument;
import com.github.hexosse.pluginframework.pluginapi.command.CommandError;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.logging.PluginLogger;
import com.github.hexosse.pluginframework.pluginapi.message.MessageColor;
import com.github.hexosse.pluginframework.pluginapi.message.MessageManager;
import com.github.hexosse.pluginframework.pluginapi.message.MessagePart;
import com.github.hexosse.pluginframework.pluginapi.message.MessageText;
import com.github.hexosse.pluginframework.pluginapi.message.predifined.Help;
import com.github.hexosse.pluginframework.pluginapi.message.predifined.SimpleMessage;
import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.apache.commons.lang.Validate;
import org.bukkit.command.*;

import java.util.*;

/**
 * This file is part of HexocubeItems
 */
public abstract class PluginCommand<PluginClass extends Plugin> extends Command implements PluginIdentifiableCommand
{
	/**
	 * The plugin that created this object.
	 */
	protected PluginClass plugin;
	/**
	 * @return The logger used by the plugin
	 */
	public PluginClass getPlugin()
	{
		return plugin;
	}

	/**
	 * The logger used by the plugin
	 */
	protected PluginLogger pluginLogger;
	/**
	 * @return The logger used by the plugin
	 */
	public PluginLogger getLogger()
	{
		return pluginLogger;
	}

	/**
	 * The message manager used by the plugin
	 */
	protected MessageManager messageManager;
	/**
	 * @return The message manager used by the plugin
	 */
	public MessageManager getMessageManager() {
		return messageManager;
	}



	/**
	 * The parent command
	 */
	private PluginCommand<?> parentCommand;

	/**
	 * List of sub commands
	 */
	private final Map<String,PluginCommand<?>> subCommands = new LinkedHashMap<String,PluginCommand<?>>();

	/**
	 * List of arguments used for the command
	 */
	private final List<CommandArgument<?>> arguments = new ArrayList<CommandArgument<?>>();



	/**
	 * @param plugin The plugin that this listener belongs to.
	 */
	public PluginCommand(String name, PluginClass plugin)
	{
		super(name);
		this.plugin = plugin;
		this.pluginLogger = plugin.getPluginLogger();
		this.messageManager = plugin.getMessageManager();
	}

	/**
	 * @param plugin The plugin that this listener belongs to.
	 */
	public PluginCommand(String name, String description, String usageMessage, List<String> aliases, PluginClass plugin)
	{
		super(name, description, usageMessage, aliases);
		this.plugin = plugin;
		this.pluginLogger = plugin.getPluginLogger();
	}



	/**
	 * Define the parent of the command
	 * @param parentCommand The parent command
	 */
	protected void setParentCommand(PluginCommand<?> parentCommand)
	{
		this.parentCommand = parentCommand;
	}

	/**
	 * @return Parent command
	 */
	public PluginCommand<?> getParentCommand()
	{
		return parentCommand;
	}

	/**
	 * @return Main command
	 */
	public PluginCommand<?> getMainCommand()
	{
		PluginCommand<?> main = getParentCommand();

		while(main!=null && main.getParentCommand()!=null)
			main = main.getParentCommand();

		return main;
	}

	/**
	 * @param subCommand Sub command to add to the actual command
	 *
	 * @return the added sub command
	 */
	public PluginCommand<?> addSubCommand(PluginCommand<?> subCommand)
	{
		subCommand.setParentCommand(this);
		this.subCommands.put(subCommand.getName(), subCommand);
		return this;
	}

	/**
	 * @param subCommandName Name of the sub command
	 *
	 * @return A sub command by its name
	 */
	private PluginCommand<?> getSubCommand(String subCommandName)
	{
		for(Map.Entry<String,PluginCommand<?>> entry : this.subCommands.entrySet())
		{
			String commandName = entry.getKey();
			PluginCommand<?> command = entry.getValue();

			if(commandName.toLowerCase().equals(subCommandName.toLowerCase()))
				return command;

			for(String alias : command.getAliases())
			{
				if(alias.equals(subCommandName))
					return command;
			}
		}
		return null;
	}

	/**
	 * @return List of all sub commands
	 */
	public Map<String,PluginCommand<?>> getSubCommands()
	{
		return this.subCommands;
	}

	/**
	 * Add an @{link CommandArgument} to the command
	 * Be aware that optional arguments MUST be after mandatory arguments
	 *
	 * A full list of argument can be like :
	 * 	<mandatory1> <mandatory2> [optional1] [optional2] [optional3]
	 *
	 * @param argument Argument to add
	 *
	 * @return The command
	 */
	public PluginCommand<?> addArgument(CommandArgument<?> argument)
	{
		// Can't add a mandatory argument after an optional argument
		if(	(argument.isMandatory() || argument.isMandatoryForConsole())
			&& this.arguments.size()>0
			&& this.arguments.get(this.arguments.size()-1).isOptional())
			throw new IllegalArgumentException("You can't add mandatory argument after an optional argument.");

		this.arguments.add(argument);
		return this;
	}


	public PluginCommand<?> removeArgument(String name)
	{
		for(CommandArgument<?> argument : this.arguments)
		{
			if(argument.getName().toLowerCase().equals(name.toLowerCase()))
			{
				this.arguments.remove(argument);
				return this;
			}
		}

		return this;
	}

	/**
	 * @return Minimum arguments used by the command
	 */
	public int getMinArgs(CommandSender sender)
	{
		// Count the number of optional arguments
		int minArgs = 0;
		for(CommandArgument arg : arguments)
			minArgs += arg.isMandatory(sender)?1:0;
		return minArgs;
	}

	/**
	 * @return Maximum arguments used by the command
	 */
	public int getMaxArgs()
	{
		return arguments.size();
	}

	public List<CommandArgument<?>> getArguments()
	{
		return arguments;
	}

	public MessagePart help()
	{
		// Full command
		String fullCommand = getName();
		PluginCommand<?> parent = getParentCommand();
		while(parent != null)
		{
			fullCommand = parent.getName() + " " + fullCommand;
			parent = parent.getParentCommand();
		}
		fullCommand = "/" + fullCommand;

		// Hover text
		ComponentBuilder fullCommandHoverText = new ComponentBuilder("");
		// Command
		fullCommandHoverText.append(MessageText.commmand_command + " : ").color(ChatColor.WHITE).append(fullCommand).color(MessageColor.COMMAND.color());
		// Aliases
		if(getAliases()!=null && getAliases().isEmpty()==false)
		{
			String aliases[] = getAliases().toArray(new String[0]);

			fullCommandHoverText.append("\n").append(MessageText.commmand_aliases + " : ").color(ChatColor.WHITE).append(aliases[0]).color(MessageColor.COMMAND.color());
			for(int i=1; i<aliases.length; i++)
				fullCommandHoverText.append(", ").append(aliases[i]).color(MessageColor.COMMAND.color());
		}
		// Description
		if(getDescription()!=null && getDescription().isEmpty()==false)
		{
			String descriptions[] = getDescription().split("\\r?\\n");

			fullCommandHoverText.append("\n").append(MessageText.commmand_description + " : ").color(ChatColor.WHITE).append(descriptions[0]).color(MessageColor.DESCRIPTION.color());
			for(int i=1; i<descriptions.length; i++)
				fullCommandHoverText.append("\n").append(descriptions[i]).color(MessageColor.DESCRIPTION.color());
		}

		fullCommandHoverText.append("\n");
		fullCommandHoverText.append("\n").append(MessageText.commmand_click_copy_command).color(MessageColor.ERROR.color()).bold(true);

		ClickEvent fullCommandClickEvent = new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,fullCommand);
		HoverEvent fullCommandHoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT,fullCommandHoverText.create());

		// full command part
		return new MessagePart(fullCommand).color(MessageColor.COMMAND).event(fullCommandClickEvent).event(fullCommandHoverEvent);
	}

	/* Internal use */
	private String getStringArg(int index, String[] args)
	{
		return (args.length>index)?args[index]:null;
	}

	/**
	 * Executes the given command, returning its success
	 * Override this method in your command
	 *
	 * @param commandInfo Info about the command
	 *
	 * @return true if a valid command, otherwise false
	 */
	public boolean onCommand(CommandInfo commandInfo) { return false; }

	/**
	 * Requests a list of possible completions for a command argument.
	 * Override this method in your command
	 *
	 * @param commandInfo Info about the command
	 *
	 * @return A List of possible completions for the final argument, or null
	 * to default to the command executor
	 */
	public List<String> onTabComplete(CommandInfo commandInfo)
	{
		List<String> completions = Lists.newArrayList();

		if(commandInfo.numArgs() == 0)
		{
			for(Map.Entry<String,PluginCommand<?>> entry : this.subCommands.entrySet())
			{
				String commandName = entry.getKey();
				completions.add(commandName);
			}
		}
		else
		{
			for(Map.Entry<String,PluginCommand<?>> entry : this.subCommands.entrySet())
			{
				if(entry.getKey().toLowerCase().startsWith(commandInfo.getLastArg().toLowerCase()))
				{
					String commandName = entry.getKey();
					completions.add(commandName);
				}
			}
		}

		return completions.size() > 0 ? completions : null;
	}

	/**
	 * Called when sender does not have necessary permissions.
	 * Override this method in your command
	 *
	 * @param sender Source object which is executing this command
	 */
	public void onPermissionRefused(CommandSender sender)
	{
		if(this.getPermissionMessage()!=null && this.getPermissionMessage().isEmpty()==false)
		{
			for (String line : this.getPermissionMessage().replace("<permission>", this.getPermission()).split("\n")) {
				SimpleMessage.severe(sender, line);
			}
		}
		else
			SimpleMessage.warnPermission(sender);
	}

	/**
	 * Override this method in your command
	 *
	 * @param commandInfo Info about the command
	 */
	public void onCommandHelp(CommandError error, CommandInfo commandInfo)
	{
		boolean isMainCommand = this.parentCommand == null;
		boolean isSubCommand = this.parentCommand != null;

		// Display help of main command
		if(isMainCommand==true)
		{
			// Send Usage Message
			Help message = new Help(error, commandInfo);
			plugin.messageManager.send(commandInfo, message);
		}

		// Display help of sub command
		else if(isSubCommand==true)
		{
			// Send Usage Message
			Help message = new Help(error, commandInfo);
			plugin.messageManager.send(commandInfo, message);
		}
	}

	/**
	 * Executes the command, returning its success
	 *
	 * @param sender       Source object which is executing this command
	 * @param commandLabel The alias of the command used
	 * @param args         All arguments passed to the command, split via ' '
	 *
	 * @return true if the command was successful, otherwise false
	 */
	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args)
	{
		boolean success = false;
		int minArgs = getMinArgs(sender);

		if(!this.plugin.isEnabled())
			return false;

		// Remove unnecessary arg
		if(args.length>0 && args[0].equals(""))
			args = Arrays.copyOfRange(args, 1, args.length);

		// Not enough parameters for the command
		if(args.length == 0 && minArgs > 0)
		{
			// Check permissions
			if(!testPermissionSilent(sender))
			{
				this.onPermissionRefused(sender);
				return false;
			}

			// Help command
			this.onCommandHelp(CommandError.NOT_ENOUGH_ARGUMENTS, new CommandInfo(sender, this, commandLabel, args, null));
			return false;
		}
		// Main command call
		else if(args.length == 0 && minArgs == 0)
		{
			// Check permissions
			if(!testPermissionSilent(sender))
			{
				this.onPermissionRefused(sender);
				return false;
			}

			// Get mandatory arguments with default values:
			Map<String,String> namedArgs = new LinkedHashMap<String,String>();
			// Loop through attended args
			int index = 0;
			for(CommandArgument<?> argument : this.arguments)
			{
				String argName = argument.getName();

				if(argument.isMandatory() && argument.hasDefaultValue())
					namedArgs.put(argName, argument.getDefaultValue().toString());
			}


			// Execute the command
			try
			{
				success = this.onCommand(new CommandInfo(sender, this, commandLabel, args, namedArgs));
			}
			catch(Throwable ex)
			{
				throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + this.plugin.getDescription().getFullName(), ex);
			}
		}
		// With multiple args it could be a SubCommand or the main command
		else if(args.length > 0)
		{
			// Sub or Base ???
			// First we check if the first arg correspond to a Sub command
			String firstArg = args[0].toLowerCase();
			// Check if a sub command exist for this arg
			PluginCommand<?> subCommand = getSubCommand(firstArg);
			// If yes, this a sub command
			if(subCommand != null)
			{
				String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
				return subCommand.execute(sender,firstArg, subArgs);
			}
			// Else, it could be the command with args
			else
			{
				// Check permissions
				if(!testPermissionSilent(sender))
				{
					this.onPermissionRefused(sender);
					return false;
				}

				// Check that the numbers of arguments correspond
				// if not, show the help command
				if(args.length < getMinArgs(sender) || args.length > (getMaxArgs() == -1 ? args.length : getMaxArgs()))
				{
					this.onCommandHelp(CommandError.NOT_ENOUGH_ARGUMENTS, new CommandInfo(sender, this, commandLabel, args, null));
					return false;
				}

				// Now that the number of arguments correspond, we need to check the validity of each args

				// Don't forget the rule :
				// 		You can't add a mandatory argument after an optional argument
				Map<String,String> namedArgs = new LinkedHashMap<String,String>();

				// Loop through attended args
				int index = 0;
				for(CommandArgument<?> argument : this.arguments)
				{
					String argName = argument.getName();
					String value = getStringArg(index, args);

					// If mandatory, the argument MUST correspond
					if(argument.isMandatory(sender))
					{
						// Check the string
						if(argument.getType().check(value))
						{
							namedArgs.put(argName, value);

							index++;
							continue;
						}
						else
						{
							this.onCommandHelp(CommandError.MISMATCH_ARGUMENTS, new CommandInfo(sender, this, commandLabel, args, null));
							return false;
						}
					}

					// Else, here begin the problems
					else if(argument.isOptional(sender))
					{
						// Check the string
						if(argument.getType().check(value))
						{
							namedArgs.put(argName, value);

							index++;
							continue;
						}
						else if(argument.hasDefaultValue() && argument.getType().check(argument.getDefaultValue().toString()))
						{
							namedArgs.put(argName, argument.getDefaultValue().toString());

							continue;
						}
					}
				}

				// We have reach the ends of possible arguments but some args are still in the queue
				if(index < args.length)
				{
					this.onCommandHelp(CommandError.MISMATCH_ARGUMENTS, new CommandInfo(sender, this, commandLabel, args, null));
					return false;
				}

				// Finally, execute the command
				try
				{
					success = this.onCommand(new CommandInfo(sender, this, commandLabel, args, namedArgs));
				}
				catch(Throwable ex)
				{
					throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + this.plugin.getDescription().getFullName(), ex);
				}
			}
		}

		return success;
	}

    /**
	 * {@inheritDoc}
	 * <p/>
	 * Delegates to the tab completer if present.
	 * <p/>
	 * If it is not present or returns null, will delegate to the current
	 * command executor if it implements {@link TabCompleter}. If a non-null
	 * list has not been found, will default to standard player name
	 * completion in {@link
	 * Command#tabComplete(CommandSender, String, String[])}.
	 * <p/>
	 * This method does not consider permissions.
	 *
	 * @throws CommandException         if the completer or executor throw an
	 *                                  exception during the process of tab-completing.
	 * @throws IllegalArgumentException if sender, alias, or args is null
	 */
	@Override
	public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args)
	throws CommandException, IllegalArgumentException
	{
		Validate.notNull(sender, "Sender cannot be null");
		Validate.notNull(args, "Arguments cannot be null");
		Validate.notNull(alias, "Alias cannot be null");

		//
		if(args.length > 0 && args[0].equals(""))
			args = Arrays.copyOfRange(args, 1, args.length);

		List<String> completions = null;
		try
		{
			if(args.length>0)
			{
				// Sub or Base ???
				// First we check if the first arg correspond to a Sub command
				String firstArg = args[0].toLowerCase();
				// Check if a sub command exist for this arg
				PluginCommand<?> subCommand = getSubCommand(firstArg);
				// If yes, this a sub command
				if(subCommand != null)
				{
					String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
					completions = subCommand.tabComplete(sender, alias, subArgs);
				}
				// Else, it could be the command with args
				else
					completions = this.onTabComplete(new CommandInfo(sender, this, alias, args, null));
			}
			else
				completions = this.onTabComplete(new CommandInfo(sender, this, alias, args, null));
		}
		catch(Throwable ex)
		{
			StringBuilder message = new StringBuilder();
			message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
			for(String arg : args)
				message.append(arg).append(' ');
			message.deleteCharAt(message.length() - 1).append("' in plugin ").append(plugin.getDescription().getFullName());
			throw new CommandException(message.toString(), ex);
		}

		if(completions == null)
		{
			return super.tabComplete(sender, alias, args);
		}
		return completions;
	}
}