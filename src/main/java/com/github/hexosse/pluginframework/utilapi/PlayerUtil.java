package com.github.hexosse.pluginframework.utilapi;

/*
 * Copyright 2016 hexosse
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

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static com.github.hexosse.pluginframework.utilapi.LocationUtil.getCardinalDistance;

/**
 * This file is part of HexocubeItems
 */
public class PlayerUtil
{
    /**
     * List of static functions :
     *
     *      isPlayerOnline : Check if a player is online.
     *      getPlayer : get player from name
     *      getFrontLocation : get location in front of the player
     *      getCardinalFrontLocation :
     *      givePlayerItem : Put the ItemStack into player inventory. If the inventory is full, then drop the item stack.
     *      getCardinalDirection : Get the cardinal compass direction of a player.
     *      getCardinalDirection : Converts a rotation to a cardinal direction.
     *
     */


    /**
     * Check if a player is online.
     *
     * @param playerName Name of a player
     */
    public static boolean isPlayerOnline(String playerName)
    {
        return Bukkit.getPlayerExact(playerName) != null;
    }

    /**
     * Get a player is online.
     *
     * @param playerName Name of a player
     */
    public static Player getPlayer(String playerName)
    {
        return Bukkit.getPlayerExact(playerName);
    }

    /**
     * Put the ItemStack into player inventory
     * If the inventory is full, then drop the item stack.
     *
     * @param player
     * @param itemStack
     */
    public static void givePlayerItem(Player player, ItemStack itemStack)
    {
        if(player==null || itemStack==null) return;

        HashMap<Integer, ItemStack> leftOver = new HashMap<Integer, ItemStack>();
        leftOver.putAll(player.getInventory().addItem(itemStack));
        if (!leftOver.isEmpty()) {
            Location location = player.getLocation();
            player.getWorld().dropItem(location, leftOver.get(0));
            for(int i=0; i<leftOver.size(); i++)
                location.getWorld().playSound(location, Sound.ITEM_PICKUP, 1.0f, 1.0f);
        }
    }


    public static Location getFrontLocation(Player player, float distance)
    {
        Location loc = player.getLocation();

        return loc.add(loc.getDirection().normalize().multiply(distance));
    }

    /**
     * Get the location in front of the player.
     *
     * @param player Player
     * @param distance Distance
     * @return The location where the player is looking at
     */
    public static Location getCardinalFrontLocation(Player player, float distance)
    {
        Location loc = player.getLocation();
        BlockFace direction = PlayerUtil.getCardinalDirection(player);

        return getCardinalDistance(loc, direction, distance);
    }

    /**
     * Get the cardinal compass direction of a player
     *
     * @param player Player
     * @return Cardinal direction player is looking at
     */
    public static BlockFace getCardinalDirection(Player player)
    {
        double rotation = (player.getLocation().getYaw()) % 360;
        if (rotation < 0)
            rotation += 360.0;

        return getCardinalDirection(rotation);
    }

    /**
     * Converts a rotation to a cardinal direction
     *
     * @param rotation Rotation to cenvert
     * @return Rotation converted into a cardinal direction
     */
    public static BlockFace getCardinalDirection(double rotation)
    {
        if (0 <= rotation && rotation < 22.5) {
            return BlockFace.SOUTH;
        } else if (22.5 <= rotation && rotation < 67.5) {
            return BlockFace.SOUTH_WEST;
        } else if (67.5 <= rotation && rotation < 112.5) {
            return BlockFace.WEST;
        } else if (112.5 <= rotation && rotation < 157.5) {
            return BlockFace.NORTH_WEST;
        } else if (157.5 <= rotation && rotation < 202.5) {
            return BlockFace.NORTH;
        } else if (202.5 <= rotation && rotation < 247.5) {
            return BlockFace.NORTH_EAST;
        } else if (247.5 <= rotation && rotation < 292.5) {
            return BlockFace.EAST;
        } else if (292.5 <= rotation && rotation < 337.5) {
            return BlockFace.SOUTH_EAST;
        } else if (337.5 <= rotation && rotation < 360.0) {
            return BlockFace.SOUTH;
        } else {
            return null;
        }
    }
}
