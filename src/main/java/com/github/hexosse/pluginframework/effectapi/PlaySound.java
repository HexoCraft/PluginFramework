package com.github.hexosse.pluginframework.effectapi;

import org.bukkit.Location;
import org.bukkit.Sound;

/**
 * This file is part of HexocubeItems
 */
public class PlaySound
{
    public static void spawn(Location location, Sound sound, float volume, float pitch)
    {
        if (location == null) return;
        location.getWorld().playSound(location, sound, volume, pitch);
    }

}
