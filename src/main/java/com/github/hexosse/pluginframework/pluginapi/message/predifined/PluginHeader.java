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

import com.github.hexosse.pluginframework.pluginapi.message.MessageLine;

import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class PluginHeader extends Header
{
	protected String pluginName;

	public PluginHeader(String plugineName)
	{
		this.pluginName = plugineName;
	}

	public PluginHeader(String plugineName, char headerChar)
	{
		this.pluginName = plugineName;
		this.headerChar = headerChar;
	}

	/**
	 * set the character used in the header
	 *
	 * @param headerChar Character used in the header
	 */
	public void setHeaderChar(char headerChar)
	{
		this.headerChar = headerChar;
	}

	@Override
	public List<MessageLine> getLines()
	{
		if(this.lines.isEmpty() == false)
			return super.getLines();

		this.add(line(this.headerChar, this.pluginName));
		return super.getLines();
	}
}
