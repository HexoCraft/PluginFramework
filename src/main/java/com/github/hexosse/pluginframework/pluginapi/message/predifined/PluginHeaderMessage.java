package com.github.hexosse.pluginframework.pluginapi.message.predifined;

import com.github.hexosse.pluginframework.pluginapi.message.MessageLine;

import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class PluginHeaderMessage extends HeaderMessage
{
	protected String pluginName;

	public PluginHeaderMessage(String plugineName)
	{
		this.pluginName = plugineName;
	}

	public PluginHeaderMessage(String plugineName, char headerChar)
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
