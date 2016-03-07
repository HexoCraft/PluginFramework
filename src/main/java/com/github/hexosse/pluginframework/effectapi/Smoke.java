package com.github.hexosse.pluginframework.effectapi;

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

import org.bukkit.Effect;
import org.bukkit.Location;

import java.util.Collection;
import java.util.Random;

/**
 * This file is part of HexocubeItems
 */
public class Smoke {

    // -----------------------------
    // Smoke Directions
    // -----------------------------
    // Direction ID    Direction
    //            0    South - East
    //            1    South
    //            2    South - West
    //            3    East
    //            4    (Up or middle ?)
    //            5    West
    //            6    North - East
    //            7    North
    //            8    North - West
    //-----------------------------

    public static Random random = new Random();

    public static void spawnSingle(Location location, int direction)
    {
        if (location == null) return;
        PlayEffect.spawn(location, Effect.SMOKE, direction);
    }

    public static void spawnSingle(Location location)
    {
        spawnSingle(location, 4);
    }

    public static void spawnSingleRandom(Location location)
    {
        spawnSingle(location, random.nextInt(9));
    }

    // Simple Cloud ========
    public static void spawnCloudSimple(Location location)
    {
        for (int i = 0; i <= 8; i++)
        {
            spawnSingle(location, i);
        }
    }

    public static void spawnCloudSimple(Collection<Location> locations)
    {
        for (Location location : locations)
        {
            spawnCloudSimple(location);
        }
    }

    // Random Cloud ========
    public static void spawnCloudRandom(Location location, float thickness)
    {
        int singles = (int) Math.floor(thickness*9);
        for (int i = 0; i < singles; i++)
        {
            spawnSingleRandom(location);
        }
    }

    public static void spawnCloudRandom(Collection<Location> locations, float thickness)
    {
        for (Location location : locations)
        {
            spawnCloudRandom(location, thickness);
        }
    }
}
