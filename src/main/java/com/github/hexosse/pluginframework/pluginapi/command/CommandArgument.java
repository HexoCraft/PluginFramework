package com.github.hexosse.pluginframework.pluginapi.command;

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

import com.github.hexosse.pluginframework.pluginapi.command.type.ArgType;
import com.github.hexosse.pluginframework.pluginapi.message.MessageColor;
import com.github.hexosse.pluginframework.pluginapi.message.MessagePart;
import com.github.hexosse.pluginframework.pluginapi.message.MessageText;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

/**
 * This class describe a command argument
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CommandArgument<T>
{
	private String name;
	private ArgType<T> type;
	private T defaultValue = null;
	boolean mandatory = true;
	boolean mandatoryForConsole = true;
	String description;

	public CommandArgument(String name, ArgType<T> type, boolean mandatory)
	{
		this(name, type, null, mandatory, mandatory, null);
	}

	public CommandArgument(String name, ArgType<T> type, boolean mandatory, boolean mandatoryForConsole)
	{
		this(name, type, null, mandatory, mandatoryForConsole, null);
	}

	public CommandArgument(String name, ArgType<T> type, boolean mandatory, boolean mandatoryForConsole, String description)
	{
		this(name, type, null, mandatory, mandatoryForConsole, description);
	}

	public CommandArgument(String name, ArgType<T> type, T defaultValue, boolean mandatory)
	{
		this(name, type, defaultValue, mandatory, mandatory, null);
	}

	public CommandArgument(String name, ArgType<T> type, T defaultValue, boolean mandatory, boolean mandatoryForConsole)
	{
		this(name, type, defaultValue, mandatory, mandatoryForConsole, null);
	}

	public CommandArgument(String name, ArgType<T> type, T defaultValue, boolean mandatory, boolean mandatoryForConsole, String description)
	{
		// Null checks
		if (name == null) throw new IllegalArgumentException("name must be different from null");

		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
		this.mandatory = mandatory;
		this.mandatoryForConsole = mandatoryForConsole;
		this.description = description;
	}

	/**
	 * @return The name of the argument
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return The type of the argument
	 */
	public ArgType<T> getType()
	{
		return type;
	}

	/**
	 * @return The default value of the argument
	 */
	public T getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * @return true if a default value exist
	 */
	public boolean hasDefaultValue()
	{
		return defaultValue != null;
	}

	/**
	 * @return true if the argument is mandatory (required)
	 */
	public boolean isMandatory()
	{
		return mandatory;
	}

	/**
	 * @return true if the argument is mandatory for the console (required)
	 */
	public boolean isMandatoryForConsole()
	{
		return mandatoryForConsole;
	}

	/**
	 * @return true if the argument is mandatory for the specified sender
	 */
	public boolean isMandatory(CommandSender sender)
	{
		// A mandatory argument with a default value is considered
		// as optional
		if(defaultValue!= null)
			return false;

		if(sender instanceof ConsoleCommandSender)
			return isMandatoryForConsole();
		else
			return isMandatory();
	}

	/**
	 * @return true if the argument is optionnal (not mandatory)
	 */
	public boolean isOptional()
	{
		return !this.isMandatory();
	}

	/**
	 * @return true if the argument is optionnal (not mandatory) for the specified sender
	 */
	public boolean isOptional(CommandSender sender) { return !this.isMandatory(sender); }

	/**
	 * @return The description of the argument
	 */
	public String getDescription()
	{
		return description;
	}

	public String getTemplate(CommandArgument<?> argument)
	{
		return argument.isMandatory() ? ("<" + argument.getName() + ">") : ("[" + argument.getName() + "]");
	}

	public MessagePart help()
	{
		ComponentBuilder argumentHoverText = new ComponentBuilder("");
		argumentHoverText.append(MessageText.argument_argument + " : ").color(ChatColor.WHITE).append(getName()).color(isMandatory()?MessageColor.ARGUMENT_MANDATORY.color():MessageColor.ARGUMENT_OPTIONAL.color());
		if(getDescription()!=null && getDescription().isEmpty()==false)
			argumentHoverText.append("\n").append(MessageText.argument_description + " : ").color(ChatColor.WHITE).append(getDescription()).color(MessageColor.DESCRIPTION.color());
		if(isMandatory())
			argumentHoverText.append("\n").append(MessageText.argument_mandatory).color(MessageColor.ERROR.color());

		HoverEvent argumentHoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT,argumentHoverText.create());
		// Add full command to the mine
		return new MessagePart(" " + getTemplate(this)).color(isMandatory()?MessageColor.ARGUMENT_MANDATORY:MessageColor.ARGUMENT_OPTIONAL).event(argumentHoverEvent);
	}
}
