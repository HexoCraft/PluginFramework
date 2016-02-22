package com.github.hexosse.pluginframework.pluginapi.message.predifined;

/*
 * Copyright 2016 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.hexosse.pluginframework.pluginapi.message.Message;
import com.github.hexosse.pluginframework.pluginapi.message.MessageSeverity;
import com.github.hexosse.pluginframework.pluginapi.message.MessageText;
import org.bukkit.command.CommandSender;

/**
 * SimpleMessage allow you to send a predifined with oone line of code.
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
        Message m = new Message(MessageSeverity.INFO);
        m.add(message);
        target.sendMessage(m.getLines().get(0).toString());
    }
    public static void help(String message, CommandSender target) {
        help(target, message);
    }

    public static void info(CommandSender target, String message)
    {
        Message m = new Message(MessageSeverity.INFO);
        m.add(message);
        target.sendMessage(m.getLines().get(0).toString());
    }
    public static void info(String message, CommandSender target) {
        info(target, message);
    }

    public static void warn(CommandSender target, String message)
    {
        Message m = new Message(MessageSeverity.WARNING);
        m.add(message);
        target.sendMessage(m.getLines().get(0).toString());
    }
    public static void warn(String message, CommandSender target) {
        warn(target, message);
    }

    public static void warnPermission(CommandSender target)
    {
        Message m = new Message(MessageSeverity.ERROR);
        m.add(MessageText.commmand_no_permission);
        target.sendMessage(m.getLines().get(0).toString());
    }

    public static void severe(CommandSender target, String message)
    {
        Message m = new Message(MessageSeverity.ERROR);
        m.add(message);
        target.sendMessage(m.getLines().get(0).toString());
    }
    public static void severe(String message, CommandSender target) {
        severe(target, message);
    }
}
