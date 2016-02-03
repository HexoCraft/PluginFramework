package com.github.hexosse.pluginframework.pluginapi.message.target;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class MessageTarget {

    private List<CommandSender> targets;

    public MessageTarget(List<CommandSender> commandSenders){
        targets = commandSenders;
    }

    public MessageTarget(CommandSender... commandSender){
        targets = Lists.newArrayList(commandSender);
    }

    public MessageTarget(CommandSender commandSender){
        targets = Lists.newArrayList(commandSender);
    }

    public List<CommandSender> getTargets() {
        return targets;
    }
}
