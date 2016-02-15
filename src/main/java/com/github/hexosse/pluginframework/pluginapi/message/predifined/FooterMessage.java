package com.github.hexosse.pluginframework.pluginapi.message.predifined;

import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.github.hexosse.pluginframework.pluginapi.message.MessageLine;
import com.github.hexosse.pluginframework.pluginapi.message.MessageSeverity;

import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class FooterMessage extends Message
{
	char footerChar = '=';

	public FooterMessage()
	{
		super();
		this.severity = MessageSeverity.INFO;
	}

	public FooterMessage(MessageSeverity severity)
	{
		super();
		this.severity = severity;
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