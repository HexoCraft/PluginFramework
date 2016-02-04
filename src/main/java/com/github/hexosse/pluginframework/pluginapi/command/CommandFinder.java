package com.github.hexosse.pluginframework.pluginapi.command;

import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.exception.CommandHandlerException;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */

public class CommandFinder
{
    protected PrintStream logOutput = System.err;
    private CommandManager commandManager;

    public CommandFinder(CommandManager commandManager)
    {
        this.commandManager = commandManager;
    }

    public void registerMethods(PluginCommand pluginCommand)
    {
        for (Method method : sortedMethods(pluginCommand))
        {
            BaseCmd baseCmdInfo = method.getAnnotation(BaseCmd.class);
            SubCmd subCmdInfo = method.getAnnotation(SubCmd.class);
            if(baseCmdInfo == null && subCmdInfo == null) continue;

            Class<?> paramTypes[] = method.getParameterTypes();
            if(paramTypes.length != 1 || !paramTypes[0].equals(CommandInfo.class))
            {
                logOutput.println(String.format(
                        "CommandManager: @SubCmd marked on '%s' from class %s, must receive only one argument of type CommandInfo.",
                        method.getName(), pluginCommand.getClass().getName()
                ));
                continue;
            }

            if(baseCmdInfo != null)
                registerMethod(pluginCommand, method, baseCmdInfo);
            if(subCmdInfo != null)
                registerMethod(pluginCommand, method, subCmdInfo);
        }
    }

    private void registerMethod(PluginCommand baseCommand, Method method, BaseCmd baseCmdInfo) {
        String name = baseCmdInfo.name();

        if (name.equals(""))
            name = method.getName();

        String permission = baseCmdInfo.permission();
        if (permission.equals(""))
            permission = null;

        SubCommand sub = commandManager.addSub(name, permission).description(baseCmdInfo.description());

        if (baseCmdInfo.allowConsole())
            sub = sub.allowConsole(true);

        sub.setHandler(buildCommandHandler(baseCommand, method));
    }

    private void registerMethod(PluginCommand baseCommand, Method method, SubCmd subCmdInfo)
    {
        String name = subCmdInfo.name();

        if (name.equals(""))
            name = method.getName();

        String permission = subCmdInfo.permission();
        if (permission.equals(""))
            permission = null;

        SubCommand sub = commandManager.addSub(name, permission)
                .minArgs(subCmdInfo.minArgs())
                .description(subCmdInfo.description())
                .usage(subCmdInfo.usage());

        if(subCmdInfo.allowConsole())
            sub = sub.allowConsole(true);

        sub.setHandler(buildCommandHandler(baseCommand, method));
    }

    private static SubCommandHandler buildCommandHandler(final PluginCommand baseCommand, final Method method)
    {
        return new SubCommandHandler()
        {
            @Override
            public void handle(CommandInfo info) throws CommandHandlerException
            {
                try
                {
                    method.invoke(baseCommand, info);
                }
                catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
                {
                    if (e.getCause() instanceof CommandHandlerException)
                    {
                        throw (CommandHandlerException) e.getCause();
                    }
                    else
                    {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    /**
     * Reflect the methods on this object, sorted by name.
     * @param handler
     * @return an ArrayList of methods.
     */
    private ArrayList<Method> sortedMethods(Object handler)
    {
        TreeMap<String, Method> methodMap = new TreeMap<String, Method>();
        for (Method method : handler.getClass().getDeclaredMethods()) {
            methodMap.put(method.getName(), method);
        }
        return new ArrayList<Method>(methodMap.values());
    }
}

