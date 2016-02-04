package com.github.hexosse.pluginframework.pluginapi.command;

import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.exception.CommandHandlerException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */

public class CommandManager implements CommandExecutor, SubCommandHandler
{
    private static final SubCommandHandler commandHandler = new CommandHandler();
    private final Map<String, SubCommand> subcommands = new LinkedHashMap<String, SubCommand>();

    /**
     * Create a new CommandManager, used for dynamic sub command handling.
     */
    public CommandManager() {
    }

    /**
     * Create a new CommandManager, used for dynamic sub command handling.
     */
    public CommandManager(String command, PluginCommand pluginCommand)
    {
        // register the commands
        pluginCommand.getPlugin().getCommand(command).setExecutor(this);
        // register the sub commands
        this.registerCommands(pluginCommand);
    }

    /** Implement onCommand so this can be registered as a CommandExecutor */
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
            commandLabel = "/" + commandLabel;
        }
        List<String> callArgs = new ArrayList<String>(Arrays.asList(args));
        handleRawCommand(sender, player, commandLabel, callArgs);
        return false;
    }


    /** Implement the SubCommandHandler interface so we can do sub-sub commands and such. */
    @Override
    public void handle(CommandInfo call) throws CommandHandlerException
    {
        String commandLabel = call.getBaseCommand() + " " + call.getSubCommand().name();
        handleRawCommand(call.getSender(), call.getPlayer(), commandLabel, call.getArgs());
    }

    /**
     * Handle a command, dispatching to the appropriate listeners.
     * @param sender A CommandSender who is the person or console who sent this command.
     * @param player A Player object (can be null)
     * @param commandLabel The current alias this command is running as
     * @param args The arguments that were passed to this command.
     */
    private void handleRawCommand(CommandSender sender, Player player, String commandLabel, List<String> args)
    {
        // Base command
        if (args.size() == 0)
        {
            //CommandInfo cmdInfo = new CommandInfo(sender, player, commandLabel, sub, callArgs);
            showUsage(sender, player, commandLabel);
            return;
        }

        // SubCmd command
        String subcommandName = args.get(0).toLowerCase();
        SubCommand sub = subcommands.get(subcommandName);

        if (sub == null) {
            showUsage(sender, player, commandLabel);
            return;
        }
        else if (!sub.checkPermission(sender))
        {
            //ChatMagic.send(sender, formatter.getPermissionWarning());
            return;
        }
        else if ((args.size() - 1) < sub.minArgs())
        {
            //String usageFormat = formatter.getUsageHeading() + "{MCMD}%s %s {USAGE}%s";
            //ChatMagic.send(sender, usageFormat, commandLabel, sub.name(), sub.usage());
            return;
        }

        List<String> callArgs = new ArrayList<String>(args.subList(1, args.size()));
        CommandInfo call = new CommandInfo(sender, player, commandLabel, sub, callArgs);
        try
        {
            sub.getHandler().handle(call);
        }
        catch (CommandHandlerException e) {
            //call.reply("{ERROR}%s", e.getMessage());
        }
        return;
    }

    /**
     * Show the usage for this command.
     * @param sender A CommandSender who is requesting the usage.
     * @param player A Player object (can be null)
     * @param commandLabel The current command label.
     * @param slash An empty string if there should be a slash prefix, a slash otherwise.
     */
    private void showUsage(CommandSender sender, Player player, String commandLabel)
    {
       /* String headerFormat = formatter.getUsageHeading() + "%s" + formatter.getUsageCommandSuffix();
        ChatMagic.send(sender, headerFormat, commandLabel);

        for (SubCommand sub: getAllowedCommand2s(sender, player)) {
            formatter.writeUsageLine(sender, commandLabel, sub);
        }*/
    }

    /**
     * Add a sub-command to this CommandManager.
     * @param name The name of this sub-command.
     * @param permission The permission string of a permission to check for this command.
     * @return a new SubCommand.
     */
    public SubCommand addSub(String name, String permission)
    {
        SubCommand cmd = new SubCommand(name, permission).setHandler(commandHandler);
        subcommands.put(name.toLowerCase(), cmd);
        return cmd;
    }

    /**
     * Add a sub-command to this CommandManager.
     * @param name the name of this sub-command.
     * @return a new SubCommand.
     */
    public SubCommand addSub(String name)
    {
        return addSub(name, null);
    }

    public void registerCommands(PluginCommand pluginCommand)
    {
        new CommandFinder(this).registerMethods(pluginCommand);
    }

    /**
     * List all registered commands allowed for a sender or a player
     * @param sender Sender
     * @param player Player
     * @return List of registered commands allowed
     */
    private List<SubCommand> getAllowedCommand2s(CommandSender sender, Player player)
    {
        ArrayList<SubCommand> items = new ArrayList<SubCommand>();
        boolean has_player = (player != null);
        for (SubCommand sub: subcommands.values())
        {
            if ((has_player || sub.allowConsole()) && sub.checkPermission(sender)) {
                items.add(sub);
            }
        }
        return items;
    }

    /**
     * List all registerd commands
     * @return List of registered commands
     */
    public List<SubCommand> getCommands()
    {
        return new ArrayList<SubCommand>(subcommands.values());
    }
}


