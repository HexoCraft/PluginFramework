package com.github.hexosse.pluginframework.effectapi;

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
import org.bukkit.World;

/**
 * This file is part HexocubeItems
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class Explosion
{
    private Location location;
    private float force;
    private boolean setFire = false;
    private boolean breakBlocks = true;


    public Explosion(Location location, int force) {
        this.location = location;
        this.force = force;
    }

    public Explosion(Location location, int force, boolean setFire, boolean breakBlocks) {
        this.location = location;
        this.force = force;
        this.setFire = setFire;
        this.breakBlocks = breakBlocks;
    }

    /**
     * Sets location of explosion
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     *
     * @return location of explosion
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Sets force of explosion
     * @param force
     */
    public void setForce(float force) {
        this.force = force;
    }

    /**
     *
     * @return force of explosion
     */
    public float getForce() {
        return this.force;
    }

    /**
     * Sets can explosion break blocks
     * @param breakBlocks
     */
    public void setBreakBlocks(boolean breakBlocks) {
        this.breakBlocks = breakBlocks;
    }

    /**
     *
     * @return true if explosion can break blocks, false otherwise
     */
    public boolean canBreakBlocks() {
        return this.breakBlocks;
    }

    /**
     * Sets will explosion set blocks fire
     * @param setFire
     */
    public void setFire(boolean setFire) {
        this.setFire = setFire;
    }

    /**
     *
     * @return will explosion set blocks fire
     */
    public boolean isSetFire() {
        return this.setFire;
    }

    /**
     * Spawns explosion to location
     * @return true if explosion exploded successfully, false otherwise
     */
    public boolean spawn() {
        Location location = this.getLocation();
        World world = location.getWorld();

        //Spawn the explosion
        return world.createExplosion(location.getX(), location.getY(), location.getZ(), this.getForce(), this.isSetFire(), this.canBreakBlocks());
    }
}

