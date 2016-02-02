package com.github.hexosse.pluginframework.pluginapi.logging;

import com.github.hexosse.pluginframework.pluginapi.BaseObject;
import com.github.hexosse.pluginframework.pluginapi.BasePlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This file is part HexocubeItems
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class PluginLogger<PluginClass extends BasePlugin> extends BaseObject<PluginClass>
{
    private Logger logger = Logger.getLogger("Minecraft");


    public PluginLogger(PluginClass plugin) {
        super(plugin);
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

