package com.github.hexosse.pluginframework.pluginapi.message;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Collections;
import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class MessageTarget
{
    private List<CommandSender> targets;

    public MessageTarget(List<CommandSender> targets) {
        this.targets = targets;
    }

    public MessageTarget(CommandSender... commandSender) {
        this.targets = Lists.newArrayList(commandSender);
    }

    public MessageTarget(CommandSender commandSender) {
        this.targets = Lists.newArrayList(commandSender);
    }

    public List<CommandSender> getTargets() {
        return targets;
    }
}
