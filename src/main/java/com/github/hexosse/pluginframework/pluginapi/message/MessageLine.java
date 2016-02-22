package com.github.hexosse.pluginframework.pluginapi.message;

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

import com.google.common.collect.Lists;

import java.util.List;

/**
 * This class represent a parts of a {@link Message}
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class MessageLine
{
	private List<MessagePart> parts;

	public MessageLine()
	{
		this.parts = Lists.newArrayList();
	}

	public MessageLine(List<MessagePart> parts)
	{
		this.parts = parts;
	}

	public MessageLine(MessagePart... parts)
	{
		this.parts = Lists.newArrayList(parts);
	}

	public MessageLine(MessagePart parts)
	{
		this.parts = Lists.newArrayList(parts);
	}

	public MessageLine add(MessagePart part)
	{
		this.parts.add(part);
		return this;
	}

	public List<MessagePart> getParts()
	{
		return this.parts;
	}

	public String toString()
	{
		String string = "";
		for(MessagePart part : parts)
		{
			string += (string.isEmpty()==false) ? " " :"";
			string += part.toString();
		}
		return string;
	}
}
