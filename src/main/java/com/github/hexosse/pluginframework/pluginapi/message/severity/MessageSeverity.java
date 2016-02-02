package com.github.hexosse.pluginframework.pluginapi.message.severity;

import org.bukkit.ChatColor;

import java.util.logging.Level;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public abstract class MessageSeverity
{
    public abstract Level getSeverity();
    public abstract ChatColor getColor();
}
