package com.github.hexosse.pluginframework.pluginapi.message.message;

import com.github.hexosse.pluginframework.pluginapi.message.severity.InfoMessageSeverity;
import com.github.hexosse.pluginframework.pluginapi.message.severity.MessageSeverity;
import com.google.common.collect.Lists;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Message
{
    protected MessageSeverity severity;
    protected List<String> messages;
    protected String prefix = "";
    protected boolean log = false;

    private boolean formated = false;


    public Message()
    {
        this.severity = new InfoMessageSeverity();
        this.messages = new ArrayList<String>();
    }

    public Message(List<String> messages)
    {
        this.severity = new InfoMessageSeverity();
        this.messages = messages;
    }

    public Message(String... messages)
    {
        this.severity = new InfoMessageSeverity();
        this.messages = Lists.newArrayList(messages);
    }

    public Message(String message)
    {
        this.severity = new InfoMessageSeverity();
        this.messages = Lists.newArrayList(message);
    }

    public Message(MessageSeverity severity)
    {
        this.severity = severity;
        this.messages = Lists.newArrayList();
    }

    public Message(MessageSeverity severity, List<String> messages)
    {
        this.severity = severity;
        this.messages = messages;
    }

    public Message(MessageSeverity severity, String... messages)
    {
        this.severity = severity;
        this.messages = Lists.newArrayList(messages);
    }

    public Message(MessageSeverity severity, String message)
    {
        this.severity = severity;
        this.messages = Lists.newArrayList(message);
    }

    public Message add(List<String> messages)
    {
        this.messages.addAll(messages);
        return this;
    }

    public Message add(String... messages)
    {
        this.messages.addAll(Lists.newArrayList(messages));
        return this;
    }

    public Message add(String message)
    {
        this.messages.add(message);
        return this;
    }

    public Message add(Message message)
    {
        if(prefix.isEmpty()==false && (message instanceof HeaderMessage || message instanceof FooterMessage))
        {
            String string = (prefix.isEmpty()==false ? prefix + " " : "");
            int length = ChatColor.stripColor(prefix).length();
            int i1 = length /2;
            int i2 = length - i1;

            String mess = message.getMessages().get(0);
            String stripMess = ChatColor.stripColor(message.getMessages().get(0));
            String color = mess.substring(0, mess.length()-stripMess.length());
            mess = color + mess.substring(i1, mess.length()-i2);

            this.messages.add(mess);
        }
        else
            this.messages.addAll(message.getMessages());
        return this;
    }

    public Message addLine()
    {
        this.messages.add(ChatColor.STRIKETHROUGH + line('-'));
        return this;
    }

    public Message addLine(char c)
    {
        this.messages.add(ChatColor.STRIKETHROUGH + line(c));
        return this;
    }

    protected String line(char c)
    {
        int max = 51 -(ChatColor.stripColor(prefix).length() + 1);
        String string = (prefix.isEmpty()==false ? prefix + " " : "");

        for(int i = 0; i < max; i++)
            string += c;
        return string;
    }

    protected String line(char c, String s)
    {
        int max = 51 -(ChatColor.stripColor(prefix).length() + 1);
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

    protected void format()
    {
        String prefix = (this.prefix.isEmpty()==false ? this.prefix + " " : "");

        for(int i=0; i<this.messages.size(); i++) {
            this.messages.add(prefix + this.severity.getColor() + this.messages.get(0));
            this.messages.remove(0);
        }

        this.formated = true;
    }

    public List<String> getMessages()
    {
        if(formated==false) format();
        return messages;
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

