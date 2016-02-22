package com.github.hexosse.pluginframework.pluginapi.message.predifined;

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

import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.github.hexosse.pluginframework.pluginapi.message.MessageLine;
import com.github.hexosse.pluginframework.pluginapi.message.MessageSeverity;
import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.logging.Level;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Footer extends Message
{
	char footerChar = '=';

	public Footer()
	{
		super();
		this.severity = MessageSeverity.INFO;
	}

	public Footer(char headerChar)
	{
		super();
		this.severity = MessageSeverity.INFO;
		this.footerChar = headerChar;
	}

	public Footer(MessageSeverity severity)
	{
		super();
		this.severity = severity;
	}

	public Footer(ChatColor color)
	{
		super();
		this.severity = new MessageSeverity(Level.INFO, color);
	}

	public Footer(MessageSeverity severity, char headerChar)
	{
		super();
		this.severity = severity;
		this.footerChar = headerChar;
	}

	public Footer(ChatColor color, char headerChar)
	{
		super();
		this.severity = new MessageSeverity(Level.INFO, color);
		this.footerChar = headerChar;
	}

	/**
	 * set the character used in the header
	 *
	 * @param footerChar Character used in the header
	 */
	public void setFooterChar(char footerChar)
	{
		this.footerChar = footerChar;
	}

	@Override
	public List<MessageLine> getLines()
	{
		if(this.lines.isEmpty() == false)
			return super.getLines();

		this.add(line(this.footerChar));
		return super.getLines();
	}
}