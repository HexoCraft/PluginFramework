package com.github.hexosse.pluginframework.pluginapi.logging;

/*
 * Copyright 2016 Hexosse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.hexosse.pluginframework.pluginapi.Plugin;
import com.github.hexosse.pluginframework.pluginapi.PluginObject;

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
    private boolean debug = false;


    public PluginLogger(PluginClass plugin) {
        super(plugin);
        this.logger = Logger.getLogger(plugin.getName());
        this.logger.setParent(Logger.getLogger("Minecraft"));
    }

    public void setDebugMode(boolean debugMode)
    {
        this.debug = debugMode;
    }

    public void debug(final String msg) { if (debug) info(msg); }
    public void help(final String msg) { this.log(Level.INFO, msg); }
    public void info(final String msg) { this.log(Level.INFO, msg); }
    public void warn(final String msg) { this.log(Level.WARNING, msg); }
    public void severe(final String msg) {
        this.log(Level.SEVERE, msg);
    }
    public void log(Level level, final String msg)
    {
        this.logger.log(level, msg);
    }
}

