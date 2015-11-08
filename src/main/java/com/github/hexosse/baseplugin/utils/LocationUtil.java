package com.github.hexosse.baseplugin.utils;

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
import org.bukkit.entity.Entity;

import java.util.HashSet;

/**
 * This file is part BasePlugin
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class LocationUtil
{
    /**
     * Returns a string representing the location
     *
     * @param location Location represented
     * @return Representation of the location
     */
    public static String locationToString(Location location)
    {
        if (location == null) throw new NullPointerException("location");

        return '[' + location.getWorld().getName() + "] " + (int)location.getBlockX() + ", " + (int)location.getBlockY() + ", " + (int)location.getBlockZ();
    }

    public static boolean equals(Location location1, Location location2)
    {
        if (location1 == null) throw new NullPointerException("location1");
        if (location2 == null) throw new NullPointerException("location2");

        return (location1.getWorld().getName()==location2.getWorld().getName())
                &&  (location1.getBlockX()==location2.getBlockX())
                &&  (location1.getBlockY()==location2.getBlockY())
                &&  (location1.getBlockZ()==location2.getBlockZ());
    }


    public static Location top(Location location)
    {
        if (location == null) throw new NullPointerException("location");

        Location top = location.clone();
        return top.add(0, 1, 0);
    }

    public static Location bottom(Location location)
    {
        if (location == null) throw new NullPointerException("location");

        Location top = location.clone();
        return top.add(0, -1, 0);
    }

    public static Entity[] getNearbyEntities(Location location, int radius)
    {
        if (location == null) throw new NullPointerException("location");

        HashSet<Entity> radiusEntities = new HashSet<Entity>();

        for (int chX = 0 - radius; chX <= radius; chX ++)
        {
            for (int chZ = 0 - radius; chZ <= radius; chZ++)
            {
                int x=(int) location.getX(),y=(int) location.getY(),z=(int) location.getZ();
                for (Entity e : new Location(location.getWorld(),x+chX,y,z+16).getChunk().getEntities())
                {
                    if (e.getLocation().distance(location) <= radius && e.getLocation().getBlock() != location.getBlock()) radiusEntities.add(e);
                }
            }
        }
        return radiusEntities.toArray(new Entity[radiusEntities.size()]);
    }

    public static void RemoveBlock(Location location)
    {
        if (location == null) throw new NullPointerException("location");

        Block b = location.getBlock();
        if(b!=null) b.setType(Material.AIR);
    }

    public static double distance(Location a, Location b)
    {
        if (a == null) throw new NullPointerException("location a");
        if (b == null) throw new NullPointerException("location b");

        return Math.sqrt(square(a.getX() - b.getX()) + square(a.getY() - b.getY()) + square(a.getZ() - b.getZ()));
    }

    public static double square(double x)
    {
        return x * x;
    }
}

