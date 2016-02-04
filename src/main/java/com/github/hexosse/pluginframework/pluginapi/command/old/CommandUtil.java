package com.github.hexosse.pluginframework.pluginapi.command.old;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

/**
 * This file is part HexocubeItems
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CommandUtil {
    private JavaPlugin p;
    private List<String> mc;
    private Map<String, String> c;
    private Map<String, String[]> help;

    /**
     * Creates a new CommandUtil under the specified plugin.
     *
     * @param p The plugin that uses this.
     */
    public CommandUtil(JavaPlugin p) {
        mc = new ArrayList<String>();
        c = new HashMap<String, String>();
        help = new HashMap<String, String[]>();
        c.put("help", "displays this message.");
        c.put("reload", "reloads the config.");
        this.p = p;
    }

    /**
     * Registers command information that can be easily obtained by the user of
     * the plugin.
     *
     * @param command The command to register the help under.
     * @param syntax The syntax of the command.
     * @param description The description of the command.
     */
    public void registerHelp(String command, String syntax, String description) {
        String[] arr = new String[2];
        arr[0] = syntax;
        arr[1] = description;
        help.put(command.toLowerCase(), arr);
    }

    /**
     * Registers command information that can be easily obtained by the user of
     * the plugin. If override is true, it will overwrite any other help
     * registered under the same command.
     *
     * @param command
     *            The command to register the help under.
     * @param syntax
     *            The syntax of the command.
     * @param description
     *            The description of the command.
     * @param override
     *            If the command is already registered, this boolean decides
     *            whether or not it will be overridden.
     */
    public void registerHelp(String command, String syntax, String description,
                             boolean override) {
        String[] arr = new String[2];
        arr[0] = syntax;
        arr[1] = description;
        if (override) {
            help.put(command.toLowerCase(), arr);
        } else {
            if (!help.containsKey(command)) {
                help.put(command.toLowerCase(), arr);
            }
        }
    }

    /**
     * Registers a new main command with the CommandUtil.
     *
     * @param command
     *            The new main command.
     */
    public void registerMainCommand(String command) {
        mc.add(command);
        this.registerHelp(
                "help",
                "/" + command + " help [subCommand]",
                "Displays the general help message or gives command-specific help.",
                false);
        this.registerHelp("reload", "/" + command + " reload",
                "reloads the config file.", false);
    }

    /**
     * Registers a new subCommand with the CommandUtil. The util already
     * includes help and reload.
     *
     * @param command
     *            The new sub command.
     * @param shortDescription
     *            The short description of this command.
     */
    public void registerSubCommand(String command, String shortDescription) {
        if (!command.equals("help") && !command.equals("reload")) {
            c.put(command, shortDescription);
        }
    }

    /**
     * Registers a new subCommand with the CommandUtil. The util already
     * includes help and reload. If override is true it will overwrite any other
     * subcommand of the same name.
     *
     * @param command
     *            The new sub command.
     * @param shortDescription
     *            The short description of this command. * @param override If
     *            the command is already registered, this boolean decides
     *            whether or not it will be overridden.
     */
    public void registerSubCommand(String command, String shortDescription,
                                   boolean override) {
        if (override) {
            c.put(command, shortDescription);
        } else {
            if (!c.containsKey(command)) {
                c.put(command, shortDescription);
            }
        }
    }

    /**
     * To be included in the onCommand method, the util will process the
     * command. This methods arguments are identical to the onCommand()
     * arguments, and should be passed directly from onCommand.
     *
     * @param sender
     *            The command sender.
     * @param cmd
     *            The command.
     * @param label
     *            The command's label.
     * @param args
     *            The command arguments.
     * @return Whether or not the command was successful
     */
    public boolean processCommand(CommandSender sender, Command cmd,
                                  String label, String[] args) {
        boolean mainCommand = false;
        Iterator<String> itr = mc.iterator();
        while (itr.hasNext()) {
            String com = itr.next();
            if (cmd.getName().equalsIgnoreCase(com)) {
                mainCommand = true;
                break;
            }
        }
        if (mainCommand) {
            if (args.length != 0) {
                Iterator<String> it = c.keySet().iterator();
                boolean isCommand = false;
                while (it.hasNext()) {
                    String com = it.next();
                    if (args[0].equalsIgnoreCase(com)) {
                        isCommand = true;
                        break;
                    }
                }

                if (isCommand) {
                    if (args[0].equalsIgnoreCase("help")) {
                        if (args.length == 1) {
                            Iterator<String> itn = c.keySet().iterator();
                            int i = 1;
                            sender.sendMessage("--- " + p.getName()
                                    + " help ---");
                            while (itn.hasNext()) {
                                String command = itn.next();
                                String description = c.get(command);
                                sender.sendMessage(String.valueOf(i) + ". "
                                        + command + " - " + description);
                                i++;
                            }
                            sender.sendMessage("----- End Help -----");
                        } else {
                            if (args.length == 2) {
                                if (help.containsKey(args[1].toLowerCase())) {
                                    String coma = args[1];
                                    String[] helpa = help.get(coma);
                                    sender.sendMessage(coma + " -");
                                    sender.sendMessage("    Syntax: "
                                            + helpa[0]);
                                    sender.sendMessage("    " + helpa[1]);
                                } else {
                                    sender.sendMessage("That is not a valid sub-command!");
                                    return false;
                                }
                            } else {
                                sender.sendMessage("Improper amount of arguments!");
                                return false;
                            }
                        }
                    }
                    if (args[0].equalsIgnoreCase("reload")) {
                        p.reloadConfig();
                        p.saveConfig();
                        p.getConfig();
                        p.saveConfig();
                        p.reloadConfig();
                        sender.sendMessage("Reloaded config!");
                    }
                } else {
                    sender.sendMessage("You did not enter a valid sub-command!");
                    return false;
                }
            } else {
                sender.sendMessage("You did not enter a sub-command!");
                return false;
            }
        }
        return true;
    }

}

