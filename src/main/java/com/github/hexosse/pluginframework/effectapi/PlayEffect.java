package com.github.hexosse.pluginframework.effectapi;

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
