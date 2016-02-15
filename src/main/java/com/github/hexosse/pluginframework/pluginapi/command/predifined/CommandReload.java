package com.github.hexosse.pluginframework.pluginapi.command.predifined;

import com.github.hexosse.pluginframework.pluginapi.Plugin;
import com.github.hexosse.pluginframework.pluginapi.PluginCommand;
import com.github.hexosse.pluginframework.pluginapi.command.CommandError;
import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import org.bukkit.plugin.PluginManager;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class CommandReload<PluginClass extends Plugin> extends PluginCommand<PluginClass>
{
	public CommandReload(PluginClass plugin, String permission)
	{
		super("reload", plugin);
		this.setPermission(permission);
	}

	/**
	 * Executes the given command, returning its success
	 *
	 * @param commandInfo Info about the command
	 *
	 * @return true if a valid command, otherwise false
	 */
	@Override
	public boolean onCommand(CommandInfo commandInfo)
	{
		PluginManager pm = this.plugin.getServer().getPluginManager();

		// Disable the plugin
		pm.disablePlugin(this.plugin);

		// Re-enable the plugin
		pm.enablePlugin(this.plugin);

		// Reload hard dependencies
		for(org.bukkit.plugin.Plugin plugin : pm.getPlugins())
		{
			if(plugin != null && plugin.getDescription() != null && plugin.isEnabled() && plugin.getDescription().getDepend() != null)
			{
				for(String depend : plugin.getDescription().getDepend())
				{
					if(depend.equalsIgnoreCase(this.plugin.getName()))
					{
						pm.disablePlugin(plugin);
						pm.enablePlugin(plugin);
					}
				}
			}
		}


		this.plugin.getPluginLogger().info("Reloaded");

		return true;
	}

	@Override
	public void onCommandHelp(CommandError error, CommandInfo commandInfo)
	{
		super.onCommandHelp(error, commandInfo);
	}
}
