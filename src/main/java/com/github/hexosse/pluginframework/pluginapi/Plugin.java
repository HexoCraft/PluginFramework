package com.github.hexosse.pluginframework.pluginapi;

/*
 * Copyright 2016 hexosse
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

import com.github.hexosse.pluginframework.pluginapi.logging.PluginLogger;
import com.github.hexosse.pluginframework.pluginapi.message.MessageManager;
import com.github.hexosse.pluginframework.pluginapi.reflexion.Reflexion;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

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
     * Stores knows commands.
     */
    Map<String, Command> knownCommands;


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

        if(isRegisteredCommand(command))
			unRegisterCommand(command);
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

    private Map<String, Command> getKnownCommands()
    {
        // return default command map
        if(this.knownCommands!=null) return this.knownCommands;

        // read command map
        Map<String, Command> knownCommands = Reflexion.getField(getCommandMap(), "knownCommands");

        // cache command map
        if(knownCommands!=null) return (this.knownCommands = knownCommands);

        return null;
    }

	private boolean isRegisteredCommand(Command command)
	{
		Map<String,Command> knownCommands = getKnownCommands();

		return knownCommands.get(command.getName().toLowerCase().trim()) != null;
	}

	private void unRegisterCommand(Command command)
	{
		Map<String,Command> knownCommands = getKnownCommands();

		knownCommands.remove(command.getName().toLowerCase().trim());
		for(String alias : command.getAliases())
		{
			alias = alias.toLowerCase().trim();
			if(knownCommands.containsKey(alias) && knownCommands.get(alias).toString().contains(this.getName()))
				knownCommands.remove(alias);
		}
	}
}
