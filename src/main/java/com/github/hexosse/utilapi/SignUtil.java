package com.github.hexosse.utilapi;

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


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.material.Attachable;

/**
 * Utility class for sign
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class SignUtil
{
    /**
     * Checks if the block is a sign
     *
     * @param block Block to check
     * @return Is this block a sign?
     */
    public static boolean isSign(Block block)
    {
        if (block == null) throw new NullPointerException("block");

        return isSign(block.getType());
    }

    /**
     * Checks if the block is a sign
     *
     * @param material Material to check
     * @return Is this material a sign?
     */
    public static boolean isSign(Material material)
    {
        if (material == null) throw new NullPointerException("material");

        if (material == Material.SIGN) return true;
        if (material == Material.SIGN_POST) return true;
        if (material == Material.WALL_SIGN) return true;

        return false;
    }

    /**
     * Return the sign if the block is a chest
     *
     * @param block Block to check
     * @return Sign or null
     */
    public static Sign getSign(Block block)
    {
        return isSign(block) ? (Sign)block.getState() : null;
    }

    /**
     * Gets the block to which the sign is attached
     *
     * @param sign Sign which is attached
     * @return Block to which the sign is attached
     */
    public static Block getAttachedBlock(Sign sign)
    {
        if (sign == null) throw new NullPointerException("sign");

        return sign.getBlock().getRelative(((Attachable) sign.getData()).getAttachedFace());
    }
}
