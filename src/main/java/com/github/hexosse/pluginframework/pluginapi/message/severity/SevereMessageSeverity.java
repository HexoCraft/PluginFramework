package com.github.hexosse.pluginframework.pluginapi.message.severity;

import org.bukkit.ChatColor;

import java.util.logging.Level;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class SevereMessageSeverity extends MessageSeverity
{
    /**
     * Instatanciate a default SevereMessageSeverity
     *
     * @return SevereMessageSeverity
     */
    public static SevereMessageSeverity get() { return new SevereMessageSeverity(); }


    @Override
    public Level getSeverity() {
        return Level.SEVERE;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.RED;
    }
}
