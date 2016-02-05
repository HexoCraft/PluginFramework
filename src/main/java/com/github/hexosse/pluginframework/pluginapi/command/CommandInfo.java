package com.github.hexosse.pluginframework.pluginapi.command;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */

public class CommandInfo
{
    private final CommandSender sender;
    private final Player player;
    private final String commandName;
    private final Command command;

    private List<String> args;

    /**
     * Create a new CommandInfo representing one commandName invocation.
     * @param sender The CommandSender who invoked this (can be a console)
     * @param player The Player who invoked this (will be null if a console)
     * @param commandName The label of the base commandName being executed (for reference)
     * @param command The Command we're executing.
     * @param args The commandName arguments.
     */
    public CommandInfo(CommandSender sender, Player player, String commandName, Command command, List<String> args)
    {
        Validate.notNull(sender);
        Validate.notEmpty(commandName);
        Validate.notNull(command);
        this.sender = sender;
        this.player = player;
        this.args = args;
        this.commandName = commandName;
        this.command = command;
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
    public Command getCommand() {
        return command;
    }

    /**
     * Get the whole list of commandName arguments.
     * @return List of arguments.
     */
    public List<String> getArgs() {
        return this.args;
    }

    /**
     * Get a specific argument.
     * @param index The argument number.
     * @return The specific argument requested.
     */
    public String getArg(int index) {
        return this.args.get(index);
    }

    /**
     * Get an argument coerced into an int.
     * @param index the location in the arguments array.
     * @return The argumnt
     */
    public int getIntArg(int index) {
        return Integer.parseInt(getArg(index));
    }

    /**
     * Get all the arguments after the specified index joined into a single string.
     *
     * This is useful if one of your last arguments is a free-form text entry
     * (like for a chat message, or editing a sign/book text)
     * @param index The index to start at (inclusive)
     * @return A single string containing all the arguments till the end
     */
    public String getJoinedArgsAfter(int index) {
        return StringUtils.join(args.subList(index, args.size()), " ");
    }

    /**
     * How many arguments we got.
     * @return Number of arguments
     */
    public int numArgs() {
        return this.args.size();
    }
}

