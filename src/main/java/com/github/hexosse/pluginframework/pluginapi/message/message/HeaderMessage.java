package com.github.hexosse.pluginframework.pluginapi.message.message;

import com.github.hexosse.pluginframework.pluginapi.message.severity.InfoMessageSeverity;
import com.github.hexosse.pluginframework.pluginapi.message.severity.MessageSeverity;
import com.google.common.collect.Lists;

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
        this.severity = new InfoMessageSeverity();
    }

    public HeaderMessage(MessageSeverity severity)
    {
        super();
        this.severity = severity;
    }

    /**
     * set the character used in the header
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

        this.messages = Lists.newArrayList(line(headerChar));
        return super.getMessages();
    }
}
