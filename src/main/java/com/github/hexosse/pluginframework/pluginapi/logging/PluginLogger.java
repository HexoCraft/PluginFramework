package com.github.hexosse.pluginframework.pluginapi.logging;

import com.github.hexosse.pluginframework.pluginapi.PluginObject;
import com.github.hexosse.pluginframework.pluginapi.Plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This file is part HexocubeItems
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class PluginLogger<PluginClass extends Plugin> extends PluginObject<PluginClass>
{
    private Logger logger;


    public PluginLogger(PluginClass plugin) {
        super(plugin);
        this.logger = Logger.getLogger(plugin.getName());
        this.logger.setParent(Logger.getLogger("Minecraft"));
    }


    public void help(String msg) { this.log(Level.INFO, msg); }

    public void info(String msg) { this.log(Level.INFO, msg); }

    public void warn(String msg) { this.log(Level.WARNING, msg); }

    public void severe(String msg) {
        this.log(Level.SEVERE, msg);
    }

    public void log(Level level, String msg)
    {
        this.logger.log(level, msg);
    }
}

