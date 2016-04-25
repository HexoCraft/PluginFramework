package com.github.hexosse.pluginframework.pluginapi.message;

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

import com.github.hexosse.pluginframework.pluginapi.message.predifined.Footer;
import com.github.hexosse.pluginframework.pluginapi.message.predifined.Header;
import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.util.ChatPaginator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Message
{
    protected MessageSeverity severity;
    protected List<MessageLine> lines;
    protected String prefix = "";
    protected boolean log = false;

    private boolean formated = false;


    public Message()
    {
        this.severity = MessageSeverity.INFO;
        this.lines = new ArrayList<MessageLine>();
    }

	public Message(MessageLine line)
	{
		this.severity = MessageSeverity.INFO;
		this.lines = Lists.newArrayList(line);
	}

	public Message(String message)
	{
		this.severity = MessageSeverity.INFO;
		this.lines = Lists.newArrayList(new MessageLine(new MessagePart(message)));
	}

	public Message(MessageSeverity severity)
	{
		this.severity = severity;
		this.lines = Lists.newArrayList();
	}

	public Message(MessageSeverity severity, MessageLine line)
	{
		this.severity = severity;
		this.lines = Lists.newArrayList(line);
	}

	public Message(MessageSeverity severity, String message)
	{
		this.severity = severity;
		this.lines = Lists.newArrayList(new MessageLine(new MessagePart(message)));
	}

	public Message(ChatColor color)
	{
		this.severity = new MessageSeverity(Level.INFO, color);
		this.lines = Lists.newArrayList();
	}

	public Message(ChatColor color, MessageLine line)
	{
		this.severity = new MessageSeverity(Level.INFO, color);
		this.lines = Lists.newArrayList(line);
	}

	public Message(ChatColor color, String message)
	{
		this.severity = new MessageSeverity(Level.INFO, color);
		this.lines = Lists.newArrayList(new MessageLine(new MessagePart(message)));
	}

	public Message add(MessageLine line)
	{
		this.lines.add(line);
		return this;
	}

	public Message add(MessagePart partAsLine)
	{
		this.lines.add(new MessageLine(partAsLine));
		return this;
	}

    public Message add(String message)
    {
        this.lines.add(new MessageLine(new MessagePart(message)));
        return this;
    }

    public Message add(Message message)
    {
        if(prefix.isEmpty()==false && (message instanceof Header || message instanceof Footer))
        {
            String string = prefix + " " ;
            int length = ChatColor.stripColor(prefix).length();
            int i1 = length /2;
            int i2 = length - i1;

            String mess = message.getLines().get(0).toString();
            String stripMess = ChatColor.stripColor(message.getLines().get(0).toString());
            String color = mess.substring(0, mess.length()-stripMess.length());
            mess = color + mess.substring(i1, mess.length()-i2);

            this.add(new Message(message.getSeverity(),mess));
        }
        else
		{
			List<MessageLine> newLines = Lists.newArrayList();
			for(MessageLine line : message.getLines())
			{
				MessageColor messageColor = new MessageColor(message.getSeverity());
				MessageLine newLine  = new MessageLine();

				for(MessagePart part : line.getParts())
				{
					MessagePart newPart = new MessagePart(part);
					if(newPart.getColor()==null) newPart.color(messageColor);

					newLine.add(newPart);
				}
				newLines.add(newLine);
			}
			this.lines.addAll(newLines);
		}
        return this;
    }

    public Message addLine()
    {
        this.add(ChatColor.STRIKETHROUGH.toString() + line('-'));
        return this;
    }

    public Message addLine(char c)
    {
        this.add(ChatColor.STRIKETHROUGH.toString() + line(c));
        return this;
    }

    protected String line(char c)
    {
        int max = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH -(ChatColor.stripColor(prefix).length() + 1);
        String string = (prefix.isEmpty()==false ? prefix + " " : "");

        for(int i = 0; i < max; i++)
            string += c;
        return string;
    }

    protected String line(char c, String s)
    {
        int max = ChatPaginator.GUARANTEED_NO_WRAP_CHAT_PAGE_WIDTH -(ChatColor.stripColor(prefix).length() + 1);
        String string = (prefix.isEmpty()==false ? prefix + " " : "");

        s = ChatColor.stripColor(s);
        int i1 = (max - (s.length()+2)) /2;
        int i2 = (max - (s.length()+2)) - i1;

        for(int i = 0; i < i1; i++)
            string += c;

        string += " ";
        string += s;
        string += " ";

        for(int i = 0; i < i2; i++)
            string += c;

        return string;
    }

    public List<MessageLine> getLines()
    {
        return lines;
    }

    public void setSeverity(MessageSeverity severity) {
        this.severity = severity;
    }

    public MessageSeverity getSeverity()
    {
        return severity;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setLog(boolean log) {
        this.log = log;
    }

    public boolean isLog() {
        return log;
    }
}

