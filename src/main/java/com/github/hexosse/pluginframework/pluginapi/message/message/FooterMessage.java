package com.github.hexosse.pluginframework.pluginapi.message.message;

import com.github.hexosse.pluginframework.pluginapi.message.severity.InfoMessageSeverity;
import com.github.hexosse.pluginframework.pluginapi.message.severity.MessageSeverity;
import com.google.common.collect.Lists;

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
        this.severity = new InfoMessageSeverity();
    }

    public FooterMessage(MessageSeverity severity)
    {
        super();
        this.severity = severity;
    }

    /**
     * set the character used in the header
     * @param footerChar Character used in the header
     */
    public void setFooterChar(char footerChar)
    {
        this.footerChar = footerChar;
    }

    @Override
    public List<String> getMessages()
    {
        if(this.messages.isEmpty()==false)
            return super.getMessages();

        this.messages = Lists.newArrayList(line(this.footerChar));
        return super.getMessages();
    }
}