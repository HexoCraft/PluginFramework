package com.github.hexosse.pluginframework.pluginapi.command;

import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.exception.CommandHandlerException;
import com.github.hexosse.pluginframework.pluginapi.message.SimpleMessage;
import com.google.common.collect.Lists;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */

public class CommandManager implements CommandExecutor, ICommandHandler
{
    private static final ICommandHandler commandHandler = new CommandHandler();
    private Command command = null;
    private final Map<String, Command> subCommands = new LinkedHashMap<String, Command>();

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
        // register the command
        pluginCommand.getPlugin().getCommand(command).setExecutor(this);
        // register the sub command
        this.registerCommands(pluginCommand);
    }

    public void registerCommands(PluginCommand pluginCommand)
    {
        new CommandFinder(this).registerMethods(pluginCommand);
    }

    /** Implement onCommand so this can be registered as a CommandExecutor */
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String commandLabel, String[] args)
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

    /** Implement the ICommandHandler interface so we can do sub-sub command and such. */
    @Override
    public void handle(CommandInfo call) throws CommandHandlerException
    {
        String commandLabel = call.getCommandName() + " " + call.getCommand().name();
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
        if (args.size()==0 && command==null)
        {
            showUsage(sender, player, commandLabel);
            return;
        }

        
        // Only BaseCmd can be executed without arguments
        if (args.size()==0)
        {
            if (!command.checkPermission(sender)) {
                SimpleMessage.warnPermission(sender);
                return;
            }

            CommandInfo call = new CommandInfo(sender, player, commandLabel, command, Lists.<String>newArrayList());
            try {
                command.getHandler().handle(call);
            } catch (CommandHandlerException e) {
                SimpleMessage.severe(sender, e.getMessage());
            }
       }
        // But when args are present, it could be BaseCmd or SubCmd
        else
        {
            // SubCmd or BaseCmd ???
            // First we check if the first arg correspond to a SubCmd
            String firstArg = args.get(0).toLowerCase();
            // Check if a sub command exist fir this arg
            Command sub = subCommands.get(firstArg);
            // If yes, this a sub command
            if (sub != null)
            {
                if (!sub.checkPermission(sender)) {
                    SimpleMessage.warnPermission(sender);
                    return;
                }
                else if ((args.size() - 1) < sub.minArgs()) {
                    //String usageFormat = formatter.getUsageHeading() + "{MCMD}%s %s {USAGE}%s";
                    //ChatMagic.send(sender, usageFormat, commandLabel, sub.name(), sub.usage());
                    return;
                }

                List<String> callArgs = new ArrayList<String>(args.subList(1, args.size()));
                CommandInfo call = new CommandInfo(sender, player, commandLabel, sub, callArgs);
                try {
                    sub.getHandler().handle(call);
                } catch (CommandHandlerException e) {
                    SimpleMessage.severe(sender, e.getMessage());
                }
            }
            // Else, it could be a command with args
            else if(command != null)
            {
                if (!command.checkPermission(sender)) {
                    SimpleMessage.warnPermission(sender);
                    return;
                }
                else if ((args.size() - 1) < command.minArgs()) {
                    //String usageFormat = formatter.getUsageHeading() + "{MCMD}%s %s {USAGE}%s";
                    //ChatMagic.send(sender, usageFormat, commandLabel, sub.name(), sub.usage());
                    return;
                }

                List<String> callArgs = new ArrayList<String>(args.subList(1, args.size()));
                CommandInfo call = new CommandInfo(sender, player, commandLabel, command, callArgs);
                try {
                    command.getHandler().handle(call);
                } catch (CommandHandlerException e) {
                    SimpleMessage.severe(sender, e.getMessage());
                }
            }
        }
        return;
    }

    /**
     * Show the usage for this command.
     * @param sender A CommandSender who is requesting the usage.
     * @param player A Player object (can be null)
     * @param commandLabel The current command label.
     */
    private void showUsage(CommandSender sender, Player player, String commandLabel)
    {
       /* String headerFormat = formatter.getUsageHeading() + "%s" + formatter.getUsageCommandSuffix();
        ChatMagic.send(sender, headerFormat, commandLabel);

        for (Command sub: getAllowedCommand2s(sender, player)) {
            formatter.writeUsageLine(sender, commandLabel, sub);
        }*/
    }

    /**
     * Add a sub-command to this CommandManager.
     * @param commandName The name of this sub-command.
     * @param permission The permission string of a permission to check for this command.
     * @return a new Command.
     */
    public Command addCommand(String commandName, String permission)
    {
        command = new Command(commandName, permission).setHandler(commandHandler);
        return command;
    }

    /**
     * Add a sub-command to this CommandManager.
     * @param subCommandName The name of this sub-command.
     * @param permission The permission string of a permission to check for this command.
     * @return a new Command.
     */
    public Command addSubCommand(String subCommandName, String permission)
    {
        Command cmd = new Command(subCommandName, permission).setHandler(commandHandler);
        subCommands.put(subCommandName.toLowerCase(), cmd);
        return cmd;
    }

    /**
     * Add a sub-command to this CommandManager.
     * @param subCommandName the name of this command.
     * @return a new Command.
     */
    public Command addSubCommand(String subCommandName)
    {
        return addSubCommand(subCommandName, null);
    }


    /**
     * List all registered command allowed for a sender or a player
     * @param sender Sender
     * @param player Player
     * @return List of registered command allowed
     */
    private List<Command> getAllowedCommands(CommandSender sender, Player player)
    {
        ArrayList<Command> items = new ArrayList<Command>();
        boolean has_player = (player != null);
        for (Command sub: subCommands.values())
        {
            if ((has_player || sub.allowConsole()) && sub.checkPermission(sender)) {
                items.add(sub);
            }
        }
        return items;
    }

    /**
     * List all registerd command
     * @return List of registered command
     */
    public List<Command> getCommand()
    {
        return new ArrayList<Command>(subCommands.values());
    }
}


