package com.github.hexosse.pluginframework.pluginapi.storage;

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

import org.bukkit.Location;
import org.bukkit.World;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents the location of a block in the world.
 */
public class SerializableLocation implements Serializable {

    private World world;
    private int x;
    private int y;
    private int z;
    private float pitch;
    private float yaw;

    /**
     * @param location the location.
     */
    public SerializableLocation(Location location){
        this(location.getWorld(),location.getBlockX(),location.getBlockY(),location.getBlockZ());
    }

    /**
     * @param world the world.
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     */
    public SerializableLocation(World world, int x, int y, int z){
        this(world,x, y, z, 0f, 0f);
    }

    /**
     * @param world the world.
     * @param x The x coordinate
     * @param y The y coordinate
     * @param z The z coordinate
     * @param pitch the pitch
     * @param yaw the yaw
     */
    public SerializableLocation(World world, int x, int y, int z, float pitch, float yaw){
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        final long longX = Double.doubleToLongBits(x);
        final long longY = Double.doubleToLongBits(y);
        final long longZ = Double.doubleToLongBits(z);
        final int intPitch = Float.floatToIntBits(pitch);
        final int intYaw = Float.floatToIntBits(yaw);


        int result = 1;
        result = prime * result + intPitch;
        result = prime * result + world.getName().hashCode();
        result = prime * result + (int) (longX ^ (longX >>> 32));
        result = prime * result + (int) (longY ^ (longY >>> 32));
        result = prime * result + intYaw;
        result = prime * result + (int) (longZ ^ (longZ >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object compare)
    {
        if(compare!=null) {
            if (this == compare) {
                return true;
            } else if (compare instanceof SerializableLocation) {
                final SerializableLocation l = (SerializableLocation) compare;
                return l.getWorldUID().equals(world.getUID()) && l.x == x && l.y == y && l.z == z && l.pitch == pitch && l.yaw == yaw;
            } else if (compare instanceof Location) {
                final Location l = (Location) compare;
                return new SerializableLocation(l).equals(this);
            }
        }
        return false;
    }

    /**
     * Gets the World.
     *
     * @return The world
     */
    public World getWorld(){
        return this.world;
    }

    /**
     * Gets the UUId of the world.
     *
     * @return The UUID
     */
    public UUID getWorldUID(){
        return this.world.getUID();
    }

    /**
     * Gets the X coordinate.
     *
     * @return The coordinate.
     */
    public int getX(){
        return this.x;
    }

    /**
     * Gets the Y coordinate.
     *
     * @return The coordinate.
     */
    public int getY(){
        return this.y;
    }

    /**
     * Gets the Z coordinate.
     *
     * @return The coordinate.
     */
    public int getZ(){
        return this.z;
    }

    /**
     * Gets the yaw.
     *
     * @return the yaw.
     */
    public float getYaw() {
        return yaw;
    }

    /**
     * Gets the pitch.
     *
     * @return the pitch.
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Creates a new Location based on this instance.
     *
     * @return the Location.
     */
    public Location toLocation() {
        return new Location(world, x, y, z, yaw, pitch);
    }

    /**
     * Creates a String representing this instance in the following format: [worldname][_][x][_][y][_][z][_][pitch][_][yaw]
     *
     * @return a String representing this instance.
     */
    @Override
    public String toString()
    {
        return (new StringBuilder())
                .append(world.getName())
                .append("_")
                .append(Integer.toString(x))
                .append("_")
                .append(Integer.toString(y))
                .append("_")
                .append(Integer.toString(z))
                .append("_")
                .append(Float.toString(pitch))
                .append("_")
                .append(Float.toString(yaw))
                .toString();
    }
}