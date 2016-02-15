package com.github.hexosse.pluginframework.pluginapi.message.predifined;

import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.github.hexosse.pluginframework.pluginapi.message.MessageLine;
import com.github.hexosse.pluginframework.pluginapi.message.MessageSeverity;

import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class HeaderMessage extends Message
{
	char headerChar = '=';

	public HeaderMessage()
	{
		super();
		this.severity = MessageSeverity.INFO;
	}

	public HeaderMessage(MessageSeverity severity)
	{
		super();
		this.severity = severity;
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

		this.add(line(this.headerChar));
		return super.getLines();
	}
}
