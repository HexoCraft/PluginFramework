package com.github.hexosse.pluginframework.pluginapi.command;

import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */

public class CommandInfo
{
    private final CommandSender sender;
    private final Player player;
    private final String commandName;
    private final PluginCommand<?> command;
    private List<String> args;
    private Map<String,String> namedArgs = new LinkedHashMap<String,String>();


    /**
     * Create a new CommandInfo representing one commandName invocation.
     * @param sender The CommandSender who invoked this (can be a console)
     * @param command The Command we're executing.
     * @param label The alias of the command used
     * @param args The commandName arguments.
     */
    public CommandInfo(CommandSender sender, PluginCommand<?> command, String label, String[] args, Map<String,String> namedArgs)
    {
        Validate.notNull(sender);
        Validate.notNull(command);

        final Player player = (sender instanceof Player) ? (Player)sender : null;

        this.sender = sender;
        this.player = player;
        this.command = command;
        this.commandName = command.getName();
        this.args = new ArrayList<String>(Arrays.asList(args));
        this.namedArgs = namedArgs;
    }

    /**
     * Get the CommandSender who invoked this.
     * @return a CommandSender.
     */
    public CommandSender getSender() {
        return sender;
    }

    /**
     * Get the player who invoked this. Can be null if running at the console.
     * @return a Player, or null if this is a console commandName
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the base commandName which was called for this sub-commandName call.
     * @return A base commandName string.
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Get the Command that invoked this call.
     * @return a Command.
     */
    public PluginCommand<?> getCommand() {
        return command;
    }

    /**
     * How many arguments we got.
     * @return Number of arguments
     */
    public int numArgs() {
        return this.args.size();
    }

    /**
     * Get the whole list of commandName arguments.
     * @return List of arguments.
     */
    public List<String> getArgs() {
        return this.args;
    }

    /**
     * Get the first argument.
     * @return The first argument.
     */
    public String getFirstArg()
    {
        if(this.args.size()>0)
            return this.args.get(0);
        return null;
    }

    /**
     * Get the last argument.
     * @return The last argument.
     */
    public String getLastArg()
    {
        if(this.args.size() > 0)
            return this.args.get(this.args.size() - 1);
        return null;
    }


    public String getNamedArg(String name)
    {
        return namedArgs.get(name);
    }

    public void setNamedArg(String name, String value)
    {
        namedArgs.put(name, value);
    }

}

