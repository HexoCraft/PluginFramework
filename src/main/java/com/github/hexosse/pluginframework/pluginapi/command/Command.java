package com.github.hexosse.pluginframework.pluginapi.command;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */

public class Command
{
    private final String name;
    private String description;
    private String usage = null;
    private final String permission;
    private int minArgs = 0;
    private boolean allowConsole = true;
    private ICommandHandler handler = null;

    /**
     * Create a new Command.
     * @param name The text of this subcommand.
     * @param permission The permission to check for this command. If null, don't use permissions.
     */
    public Command(String name, String permission)
    {
        Validate.notEmpty(name);
        this.name = name;
        this.permission = permission;
    }

    /**
     * Get the command's name.
     * @return The command's name.
     */
    public String name() {
        return name;
    }

    /**
     * Set the command's description.
     *
     * Command description should be a short blurb (one line) explaining what
     * the command does. Long descriptions will probably wrap poorly.
     * .
     * @param description Description text.
     * @return the Command, useful for chaining.
     */
    public Command description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get the command's description.
     * @return The command's description.
     */
    public String description() {
        return description;
    }

    /**
     * Set this command's usage string.
     *
     * The usage string describes the parameters to this command in a visual
     * manner, for display to the user.
     *
     * @param usage a usage string.
     * @return the Command, useful for chaining.
     */
    public Command usage(String usage) {
        this.usage = usage;
        return this;
    }

    /**
     * Get the usage string.
     * @return The usage string.
     */
    public String usage() {
        return this.usage;
    }

    /**
     * Get the permission node this command is required to use.
     * @return permission node, or null if no permission.
     */
    public String permission() {
        return permission;
    }

    /**
     * Set the minimum number of arguments this command accepts.
     *
     * If minArgs is >0, then the handler will not be executed unless this
     * many arguments to the command are present.
     *
     * @param minArgs The minimum number of args
     * @return the Command, useful for chaining.
     */
    public Command minArgs(int minArgs) {
        Validate.isTrue(minArgs >= 0, "minArgs cannot be negative");
        this.minArgs = minArgs;
        return this;
    }

    /** Check what the minimum number of args is */
    public int minArgs() {
        return minArgs;
    }

    /**
     * Allow this command to be used on the console.
     * If this is not set, then the command only works for players.
     */
    public Command allowConsole(boolean allowConsole) {
        this.allowConsole = allowConsole;
        return this;
    }

    /** If true, console access is allowed. */
    public boolean allowConsole() {
        return this.allowConsole;
    }

    /**
     * Set the ICommandHandler.
     * @param handler A ICommandHandler which is called when this command executes.
     * @return the Command, useful for chaining.
     */
    public Command setHandler(ICommandHandler handler) {
        Validate.notNull(handler);
        this.handler = handler;
        return this;
    }

    /**
     * Get the currently set ICommandHandler.
     * @return the ICommandHandler which is called when this command executes.
     */
    public ICommandHandler getHandler() {
        return handler;
    }

    /**
     * Check if a Player/CommandSender has the requisite permission for this command.
     * @param sender a ConsoleSender or Player.
     * @return True if the user has the permission or the command has no permission set, false otherwise.
     */
    public boolean checkPermission(CommandSender sender)
    {
        if (permission == null) return true;
        return sender.hasPermission(permission);
    }
}

