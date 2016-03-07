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

import com.github.hexosse.pluginframework.pluginapi.Plugin;
import com.github.hexosse.pluginframework.pluginapi.PluginObject;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Cette classe se charge de toute la coloration syntaxique lors de l'envoi de predifined
 * Le but de cette classe est de standardiser l'envoi des lines dans le plugins
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class MessageManager<PluginClass extends Plugin> extends PluginObject<PluginClass>
{

    /**
     * @param plugin Plugin
     */
    public MessageManager(PluginClass plugin) { super(plugin); }

    public void send(CommandSender sender, Message message)
    {
        send(new MessageTarget(sender), message);
    }

    public void send(MessageTarget target, Message message)
    {
        List<CommandSender> targets = target.getTargets();
        List<MessageLine> lines = message.getLines();
        for(MessageLine line : lines)
        {
            for (CommandSender sender : targets)
            {
                if(sender instanceof ConsoleCommandSender)
                    sender.sendMessage((message.getPrefix().isEmpty() == true ? "" : message.getPrefix() + " ") + message.getSeverity().color() + line.toString());
                if(sender instanceof Player)
                    send((Player)sender, message.getPrefix(), message.getSeverity().color(), line);
            }
            if (message.isLog())
                pluginLogger.log(message.getSeverity().severity(), ChatColor.stripColor(line.toString()));
        }
    }

    public void send(CommandInfo commandInfo, Message message)
    {
        MessageTarget target = new MessageTarget();
        if (commandInfo.getPlayer() != null)
            target.add(commandInfo.getPlayer());
        if (commandInfo.getSender() != null && commandInfo.getPlayer() != commandInfo.getSender())
            target.add(commandInfo.getSender());
        send(target,message);
    }

    public void broadcast(Message message)
    {
        List<MessageLine> lines = message.getLines();
        for(MessageLine line : lines)
        {
            for(Player player : Bukkit.getServer().getOnlinePlayers())
            {
                send(player, (message.getPrefix().isEmpty() == true ? "" : message.getPrefix() + " "), message.getSeverity().color(), line);
            }
            if(message.isLog())
                pluginLogger.log(message.getSeverity().severity(), ChatColor.stripColor(line.toString()));
        }
    }

    private void send(Player player, String prefix, ChatColor messageColor, MessageLine line)
    {
        ComponentBuilder builder = new ComponentBuilder(prefix.isEmpty() ? "" : prefix + " ");

        for(MessagePart part : line.getParts())
        {
			ChatColor color = part.getColor()==null ? messageColor : part.getColor();
			builder.append(part.getText()).color(color).event(part.getClickEvent()).event(part.getHoverEvent());
		}

        player.spigot().sendMessage(builder.create());
    }
}

