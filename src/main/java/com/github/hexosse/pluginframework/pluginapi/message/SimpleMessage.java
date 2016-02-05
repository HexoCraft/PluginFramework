package com.github.hexosse.pluginframework.pluginapi.message;

import com.github.hexosse.pluginframework.pluginapi.message.message.Message;
import com.github.hexosse.pluginframework.pluginapi.message.severity.HelpMessageSeverity;
import com.github.hexosse.pluginframework.pluginapi.message.severity.InfoMessageSeverity;
import com.github.hexosse.pluginframework.pluginapi.message.severity.SevereMessageSeverity;
import com.github.hexosse.pluginframework.pluginapi.message.severity.WarningMessageSeverity;
import org.bukkit.command.CommandSender;

/**
 * SimpleMessage allow you to send a message with oone line of code.
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class SimpleMessage
{
    public static void send(CommandSender target, String message) {
        target.sendMessage(message);
    }
    public static void send(String message, CommandSender target) {
        send(target, message);
    }

    public static void help(CommandSender target, String message)
    {
        Message m = new Message(new HelpMessageSeverity());
        m.add(message);
        target.sendMessage(m.getMessages().get(0));
    }
    public static void help(String message, CommandSender target) {
        help(target, message);
    }

    public static void info(CommandSender target, String message)
    {
        Message m = new Message(new InfoMessageSeverity());
        m.add(message);
        target.sendMessage(m.getMessages().get(0));
    }
    public static void info(String message, CommandSender target) {
        info(target, message);
    }

    public static void warn(CommandSender target, String message)
    {
        Message m = new Message(new WarningMessageSeverity());
        m.add(message);
        target.sendMessage(m.getMessages().get(0));
    }
    public static void warn(String message, CommandSender target) {
        warn(target, message);
    }

    public static void warnPermission(CommandSender target)
    {
        Message m = new Message(new WarningMessageSeverity());
        m.add("You do not have permissions for this command.");
        target.sendMessage(m.getMessages().get(0));
    }

    public static void severe(CommandSender target, String message)
    {
        Message m = new Message(new SevereMessageSeverity());
        m.add(message);
        target.sendMessage(m.getMessages().get(0));
    }
    public static void severe(String message, CommandSender target) {
        severe(target, message);
    }
}
