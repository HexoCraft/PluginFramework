package com.github.hexosse.pluginframework.utilapi;

/*
 * Copyright 2015 Hexosse
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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.MaterialData;
import org.bukkit.util.BlockIterator;

/**
 * This file is part of HexocubeItems
 */
public class BlockUtil
{
    /**
     * List of static functions :
     *
     *      getTargetBlock : Gets the block targeted by a LivingEntity.
     *
     */


    /**
     * Checks if the block is air
     *
     * @param block Block to check
     * @return Is this block air?
     */
    public static boolean isAir(Block block)
    {
        if (block == null) throw new NullPointerException("block");

        return block.getType() == Material.AIR;
    }

    public static boolean isHalfBlock(Block block)
    {
        if (block == null) throw new NullPointerException("block");

        return isHalfBlock(block.getType());
    }

    public static boolean isHalfBlock(Material material)
    {
        if (material == null) throw new NullPointerException("material");

        if (material == Material.STEP) return true;
        if (material == Material.STONE_PLATE) return true;
        if (material == Material.WOOD_PLATE) return true;
        if (material == Material.SNOW) return true;
        if (material == Material.WOOD_STEP) return true;
        if (material == Material.GOLD_PLATE) return true;
        if (material == Material.IRON_PLATE) return true;
        if (material == Material.DAYLIGHT_DETECTOR) return true;
        if (material == Material.CARPET) return true;

        return false;
    }

    public static Location getCenter(Block block)
    {
        Location loc = block.getLocation();
        double x = loc.getBlockX();
        double y = loc.getBlockY();
        double z = loc.getBlockZ();

        return new Location(loc.getWorld(),
                /*x = x<0 ? x-0.5 :*/ x+0.5,
                /*y = y<0 ? y-0.5 :*/ y+0.5,
                /*z = z<0 ? z-0.5 :*/ z+0.5);
    }

    /**
     * Opens the holder's inventory GUI
     *
     * @param holder Inventory holder
     * @param player Player on whose screen the GUI is going to be shown
     * @return Was the opening successful?
     */
    public static boolean openBlockGUI(InventoryHolder holder, Player player)
    {
        if (holder == null) throw new NullPointerException("holder");
        if (player == null) throw new NullPointerException("player");

        Inventory inventory = holder.getInventory();
        player.openInventory(inventory);

        return true;
    }

    /**
     * @param block Block
     * @return Material of the block
     */
    public static Material getMaterial(Block block)
    {
        if (block == null) throw new NullPointerException("block");

        return block.getType();
    }

    /**
     * @param block Block
     * @return Durability of the block
     */
    public static short getDurability(Block block)
    {
        if (block == null) throw new NullPointerException("block");

        MaterialData md = block.getState().getData();
        return md.toItemStack().getDurability();
    }

    /**
     * @param b1 First block to compare
     * @param b2 Second block to compare
     * @return Comparaison result
     */
    public static boolean compare(Block b1, Block b2)
    {
        if (b1 == null) throw new NullPointerException("block b1");
        if (b2 == null) throw new NullPointerException("block b2");

        return ( getMaterial(b1)==getMaterial(b2) && getDurability(b1)==getDurability(b2) );
    }

    /**
     * Gets the block targeted by a LivingEntity.
     *
     * @param entity The LivingEntity.
     * @param range The range.
     *
     * @return The block.
     */
    public static final Block getTargetBlock(final LivingEntity entity, final int range)
    {
        /* Lors des test regarder égalemene Player.getTargetBlock */
        final BlockIterator bit = new BlockIterator(entity, range);
        while(bit.hasNext())
        {
            final Block next = bit.next();
            if(next != null && next.getType() != Material.AIR) {
                return next;
            }
        }
        return null;
    }

    /*public static Block getBlockAtDistance(LivingEntity entity, int range, boolean stop_at_solid)
    {
        BlockIterator iterator = new BlockIterator(entity, range);

        while (iterator.hasNext()) {
            Block b = iterator.next();

            if (iterator.hasNext()) {
                if (stop_at_solid) {
                    if (b.getType() == Material.AIR
                            || b.getType() == Material.VINE
                            || b.getType() == Material.TORCH
                            || b.getType() == Material.DEAD_BUSH
                            || b.getType() == Material.SIGN_POST
                            || b.getType() == Material.WALL_SIGN) {
                        continue;
                    } else {
                        return b;
                    }
                } else {
                    continue;
                }
            } else {
                return b;
            }
        }

        return null;
    }*/

}
