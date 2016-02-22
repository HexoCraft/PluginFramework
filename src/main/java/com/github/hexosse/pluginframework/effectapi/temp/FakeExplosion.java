package com.github.hexosse.pluginframework.effectapi.temp;

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

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.HashSet;
import java.util.Set;

/**
 * This file is part HexocubeItems
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class FakeExplosion
{
    public static void fakeExplosion(Location location)
    {
        fakeExplosion(location, 4);
    }

    public static void fakeExplosion(Location location, int radius)
    {
        if (location == null) return;
        World world = location.getWorld();
        Set<Block> blocks = new HashSet<Block>();
        int r2 = radius * radius;
        for(int x = -radius; x <= radius; x++)
        {
            for(int y = -radius; y <= radius; y++)
            {
                for(int z = -radius; z <= radius; z++)
                {
                    if(x*x + y*y + z*z + x+y+z + 0.75 > r2) continue;
                    Block toadd = world.getBlockAt((int)(location.getX()+x+0.5), (int)(location.getY()+x+0.5), (int)(location.getZ()+x+0.5));
                    if (toadd == null) continue;
                    if (toadd.getType() != Material.AIR) continue;
                    blocks.add(toadd);
                }
            }
        }
        fakeExplosion(location, blocks);
    }

    protected static void fakeExplosion(Location location, Set<Block> blocks)
    {
        if (blocks == null) return;
        if (blocks.size() == 0) return;

        /*HashSet<ChunkPosition> chunkPositions = new HashSet<ChunkPosition>(blocks.size());

        for (Block block : blocks)
        {
            chunkPositions.add(new ChunkPosition(block.getX(), block.getY(), block.getZ()));
        }

        Packet60Explosion packet = new Packet60Explosion(location.getX(),location.getY(), location.getZ(), 0.1f, chunkPositions);
        CraftServer craftServer = (CraftServer) Bukkit.getServer();
        MinecraftServer minecraftServer = craftServer.getServer();

        minecraftServer.serverConfigurationManager.sendPacketNearby(location.getX(), location.getY(), location.getZ(), 64, ((CraftWorld)location.getWorld()).getHandle().dimension, packet);
   */ }
}
