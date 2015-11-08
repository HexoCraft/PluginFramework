package com.github.hexosse.baseplugin.logging;

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

import com.github.hexosse.baseplugin.BaseObject;
import com.github.hexosse.baseplugin.BasePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The logger used by the plugin
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class PluginLogger<PluginClass extends BasePlugin> extends BaseObject<PluginClass>
{
    /**
     * The logger.
     */
    private Logger logger;


    /**
     * @param plugin	The plugin that this object belong to.
     */
    public PluginLogger(PluginClass plugin) {
        super(plugin);
        this.logger = Logger.getLogger("Minecraft");
    }


    /**
     * Logs a message with the INFO level.
     *
     * @param msg The message to be logged.
     */
    public void info(String msg) {
        this.log(Level.INFO, msg, null);
    }

    /**
     * Logs a message with the INFO level.
     *
     * @param msg The message to be logged.
     * @param player The player that will receive the msg.
     */
    public void info(String msg, Player player) {
        this.log(Level.INFO, msg, player);
    }

    /**
     * Logs a message with the WARNING level.
     *
     * @param msg The message to be logged.
     */
    public void warn(String msg) {
        this.log(Level.WARNING, msg, null);
    }

    /**
     * Logs a message with the WARNING level.
     *
     * @param msg The message to be logged.
     * @param player The player that will receive the msg.
     */
    public void warn(String msg, Player player) {
        this.log(Level.WARNING, msg, player);
    }

    /**
     * Logs a message with the SEVERE level.
     *
     * @param msg The message to be logged.
     */
    public void fatal(String msg) {
        this.log(Level.SEVERE, msg, null);
    }

    /**
     * Logs a message with the SEVERE level.
     *
     * @param msg The message to be logged.
     * @param player The player that will receive the msg.
     */
    public void fatal(String msg, Player player) {
        this.log(Level.SEVERE, msg, player);
    }


    /**
     * @param level Log level
     * @param msg The message to be logged.
     * @param player The player that will receive the msg.
     */
    public void log(Level level, String msg, Player player)
    {
        PluginDescriptionFile pdf = plugin.getDescription();
        String logPrefixColored = ChatColor.GREEN + "[" + pdf.getName() + "]: " + ChatColor.WHITE + msg;
        String logPrefixPlain = ChatColor.stripColor(logPrefixColored);

        if(player != null)
        {
            player.sendMessage(logPrefixColored);
        }
        else
        {
            ConsoleCommandSender sender = Bukkit.getConsoleSender();
            if (level == Level.INFO && sender != null)
            {
                Bukkit.getConsoleSender().sendMessage(logPrefixColored);
            } else
            {
                this.logger.log(level, logPrefixPlain);
            }
        }
    }


    /**
     * @param msg The message to be logged.
     * @param player The player that will receive the msg.
     */
    public void help(String msg, Player player)
    {
        /* L'aide ne s'adresse qu'au joueur*/
        if(player==null) return;

        player.sendMessage(msg);
    }
}
