package com.github.hexosse.pluginframework.pluginapi.message.message;

import com.google.common.collect.Lists;

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
    public List<String> getMessages()
    {
        if(this.messages.isEmpty()==false)
            return super.getMessages();

        this.messages = Lists.newArrayList(line(headerChar, pluginName));
        return super.getMessages();
    }
}
