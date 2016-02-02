package com.github.hexosse.pluginframework.pluginapi.message.severity;

import org.bukkit.ChatColor;

import java.util.logging.Level;


/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class HelpMessageSeverity extends MessageSeverity
{
    /**
     * Instatanciate a default HelpMessageSeverity
     *
     * @return HelpMessageSeverity
     */
    public static HelpMessageSeverity get() { return new HelpMessageSeverity(); }


    @Override
    public Level getSeverity() {
        return Level.INFO;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.AQUA;
    }
}
