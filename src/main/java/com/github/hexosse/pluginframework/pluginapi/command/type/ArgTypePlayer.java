package com.github.hexosse.pluginframework.pluginapi.command.type;

/*
 * Copyright 2016 Hexosse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.github.hexosse.pluginframework.pluginapi.command.CommandInfo;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ArgTypePlayer implements ArgType<Player>
{
	private ArgTypePlayer() {};
	private static ArgTypePlayer t = new ArgTypePlayer();
	public static ArgTypePlayer get() { return t; }

	@Override
	public boolean check(String playerName)
	{
		return get(playerName) != null;
	}

	@Override
	public Player get(String playerName)
	{
		try
		{
			return Bukkit.getServer().getPlayer(playerName);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public List<String> tabComplete(CommandInfo commandInfo)
	{
		String lastWord = commandInfo.numArgs() == 0 ? "" : commandInfo.getArgs().get(commandInfo.numArgs()-1);

		ArrayList<String> matchedPlayers = new ArrayList<String>();
		for(Player player : commandInfo.getSender().getServer().getOnlinePlayers())
		{
			String name = player.getName();
			if((commandInfo.getPlayer() == null || commandInfo.getPlayer().canSee(player)) && StringUtil.startsWithIgnoreCase(name, lastWord))
				matchedPlayers.add(name);
		}

		return matchedPlayers;
	}
}
