package com.github.hexosse.pluginframework.pluginapi.message;

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
