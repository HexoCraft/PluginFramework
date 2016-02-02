package com.github.hexosse.pluginframework.pluginapi.message.severity;

import org.bukkit.ChatColor;

import java.util.logging.Level;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CustomMessageSeverity extends MessageSeverity
{
    private Level severity;
    private ChatColor color;


    public CustomMessageSeverity(Level severity, ChatColor color) {
        this.severity = severity;
        this.color = color;
    }

    @Override
    public Level getSeverity() { return this.severity; }

    @Override
    public ChatColor getColor() { return this.color; }
}
