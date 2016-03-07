package com.github.hexosse.pluginframework.pluginapi.message.predifined;

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

import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandArgument;
import com.github.hexosse.pluginframework.pluginapi.command.CommandError;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import com.github.hexosse.pluginframework.pluginapi.message.*;

/**
 * This messgae is used to format command message send to user for help
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Help extends Message
{
	public Help(CommandInfo commandInfo)
	{
		this(null, commandInfo);
	}

	public Help(CommandError error, CommandInfo commandInfo)
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
		// - Description
		// The short text is the first line of the description
		if(command.getDescription()!=null && command.getDescription().isEmpty()==false)
		{
			String descriptions[] = command.getDescription().split("\\r?\\n");
			line.add(new MessagePart(" " + descriptions[0]).color(MessageColor.DESCRIPTION));
		}

		//
		this.add(line);
	}
}
