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
import org.bukkit.Material;

/**
 * This file is part of HexocubeItems
 */
public class PlayEffect
{
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

    public static void spawn(Location location, Effect effect, int direction)
    {
        if (location == null) return;
        location.getWorld().playEffect(location, effect, direction);
    }

    /**
     * Donne l'effet du block qui se casse
     * Utiliser des block dnas le material pour que l'effet fonctionne
     *
     * @param location Location de l'effet
     * @param material Material utilisé
     */
    public static void spawn(Location location, Material material)
    {
        if (location == null) return;
        location.getWorld().playEffect(location, Effect.STEP_SOUND, material);
    }

}
