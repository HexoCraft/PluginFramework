package com.github.hexosse.pluginframework.pluginapi;

/*
 * Copyright 2015 Hexosse
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

import com.github.hexosse.pluginframework.pluginapi.logging.PluginLogger;
import com.github.hexosse.pluginframework.pluginapi.message.MessageManager;
import com.github.hexosse.pluginframework.pluginapi.reflexion.Reflexion;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Listener;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The base class that the main plugin class should extend.
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public abstract class Plugin extends JavaPlugin
{
    /**
     * The logger used by the plugin
     */
    public PluginLogger pluginLogger = new PluginLogger(this);

    /**
     * Message manager used to standardise lines
     */
    public MessageManager messageManager = new MessageManager(this);

    /**
     * Stores the proper bukkit command map.
     */
    private CommandMap commandMap = null;


    /**
     * @return The logger used by the plugin
     */
    public PluginLogger getPluginLogger() {
        return pluginLogger;
    }

    /**
     * @return Message manager
     */
    public MessageManager getMessageManager() {
        return messageManager;
    }


    public void registerEvents(PluginListener listener)
    {
        if(!this.isEnabled()) {
            throw new IllegalPluginAccessException("Plugin attempted to register " + listener + " while not enabled");
        }
        this.getServer().getPluginManager().registerEvents(listener, this);
    }


    public void registerCommands(Command command)
    {
        if(!this.isEnabled())
            throw new IllegalPluginAccessException("Plugin attempted to register " + command + " while not enabled");

        getCommandMap().register(this.getDescription().getName(),command);
    }

    private CommandMap getCommandMap()
    {
        // return default command map
        if(this.commandMap!=null) return this.commandMap;

        // read command map
        CommandMap commandMap = Reflexion.getField(this.getServer().getPluginManager(), "commandMap");

        // cache command map
        if(commandMap!=null) return (this.commandMap = commandMap);

        return null;
    }
}
