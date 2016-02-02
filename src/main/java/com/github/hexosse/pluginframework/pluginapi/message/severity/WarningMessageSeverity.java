package com.github.hexosse.pluginframework.pluginapi.message.severity;

import org.bukkit.ChatColor;

import java.util.logging.Level;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class WarningMessageSeverity extends MessageSeverity
{
    /**
     * Instatanciate a default WarningMessageSeverity
     *
     * @return WarningMessageSeverity
     */
    public static WarningMessageSeverity get() { return new WarningMessageSeverity(); }


    @Override
    public Level getSeverity() {
        return Level.WARNING;
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GOLD;
    }
}
