package com.github.hexosse.pluginframework.pluginapi.permissions;

/*
 * Copyright 2015 hexosse
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


import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single permission that is used by the plugin.
 *
 * @author Jacek Kuzemczak
 */
public class PluginPermission {

    protected String node;
    protected PermissionDefault defaultValue;
    protected String description;

    public PluginPermission(String node)
    {
        this(node, PermissionDefault.FALSE);
    }

    public PluginPermission(String node, PermissionDefault defaultValue)
    {
        this(node, defaultValue, "");
    }

    public PluginPermission(String node, PermissionDefault defaultValue, String description)
    {
        this.node = node;
        this.defaultValue = defaultValue;
        this.description = description;
    }

    /**
     * Gets the node for this permission.
     *
     * @return The node.
     */
    public String getNode()
    {
        return this.node;
    }

    /**
     * Gets the {@link PermissionDefault} for this permission.
     *
     * @return The default.
     */
    public PermissionDefault getDefault()
    {
        return this.defaultValue;
    }

    /**
     * Gets a short description of what this permission allows.
     *
     * @return The description.
     */
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Gets a list of all players on the server with this permission.
     *
     * @return The list of players.
     */
    public List<Player> getPlayersWith()
    {
        ArrayList<Player> players = new ArrayList<Player>();

        for(Player player : Bukkit.getServer().getOnlinePlayers())
        {
            if(this.has(player))
            {
                players.add(player);
            }
        }

        return players;
    }

    /**
     * Checks to see if a {@link CommandSender} has this permission.
     *
     * @param sender The sender to be checked, often a {@link Player}
     *
     * @return True if the sender has this permission false if not.
     */
    public boolean has(CommandSender sender)
    {
        return sender.hasPermission(this.node);
    }
}
