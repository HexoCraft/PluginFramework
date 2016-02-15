package com.github.hexosse.pluginframework.pluginapi.message;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class MessageTarget
{
    private List<CommandSender> targets;

    public MessageTarget() {
        this.targets = Lists.newArrayList();
    }

    public MessageTarget(List<CommandSender> targets) {
        this.targets = targets;
    }

    public MessageTarget(CommandSender... commandSender) {
        this.targets = Lists.newArrayList(commandSender);
    }

    public MessageTarget(CommandSender commandSender) {
        this.targets = Lists.newArrayList(commandSender);
    }

    public void add(CommandSender commandSender) {
		if(commandSender!=null)
			this.targets.add(commandSender);
    }

    public List<CommandSender> getTargets() {
        return targets;
    }
}
