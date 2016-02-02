package com.github.hexosse.pluginframework.pluginapi.message.severity;

import org.bukkit.ChatColor;

import java.util.logging.Level;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class InfoMessageSeverity extends MessageSeverity
{
    /**
     * Instatanciate a default InfoMessageSeverity
     *
     * @return InfoMessageSeverity
     */
    public static InfoMessageSeverity get() { return new InfoMessageSeverity(); }


    @Override
    public Level getSeverity() {
        return Level.INFO;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.WHITE;
    }
}
