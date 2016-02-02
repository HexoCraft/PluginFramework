package com.github.hexosse.pluginframework.pluginapi.message;

import com.github.hexosse.pluginframework.pluginapi.BaseObject;
import com.github.hexosse.pluginframework.pluginapi.BasePlugin;
import com.github.hexosse.pluginframework.pluginapi.message.message.Message;
import com.github.hexosse.pluginframework.pluginapi.message.target.MessageTarget;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Cette classe se charge de toute la coloration syntaxique lors de l'envoi de message
 * Le but de cette classe est de standardiser l'envoi des messages dans le lpugins
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class MessageManager<PluginClass extends BasePlugin> extends BaseObject<PluginClass> {

    /**
     * @param plugin Plugin
     */
    public MessageManager(PluginClass plugin) { super(plugin); }



    public void send(MessageTarget target, Message message)
    {
        List<CommandSender> targets = target.getTargets();
        List<String> messages = message.getMessages();
        for(String mess : messages)
        {
            for (CommandSender sender : targets)
                sender.sendMessage(mess);
            if (message.isLog())
                pluginLogger.log(message.getSeverity().getSeverity(), ChatColor.stripColor(mess));
        }
    }

    public void broadcast(Message message)
    {
        List<String> messages = message.getMessages();
        for(String mess : messages)
        {
            plugin.getServer().broadcastMessage(mess);
            if(message.isLog())
                pluginLogger.log(message.getSeverity().getSeverity(), ChatColor.stripColor(mess));
        }
    }

}

