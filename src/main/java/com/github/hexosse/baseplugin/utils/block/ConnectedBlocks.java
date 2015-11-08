package com.github.hexosse.baseplugin.utils.block;

import com.github.hexosse.baseplugin.utils.block.BlockUtil;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;

/**
 * This file is part BasePlugin
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ConnectedBlocks
{
    private static BlockFace[] FACES;

    private static ArrayList<Location> unchecked = new ArrayList<>();
    private static ArrayList<Location> confirmed = new ArrayList<>();

    static { FACES = new BlockFace[]{BlockFace.UP, BlockFace.DOWN, BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}; }

    public static ArrayList<Location> getConnectedBlocks(Location location)
    {
        if (location == null) throw new NullPointerException("location");

        findConnectedBlocks(location.getBlock());
        return confirmed;
    }

    private static int findConnectedBlocks(Block block)
    {
        unchecked.clear();
        confirmed.clear();
        unchecked.add(block.getLocation());

        while(unchecked.size() > 0)
        {
            Location uncheckedLocation = unchecked.get(0);
            Block uncheckedBlock = unchecked.get(0).getBlock();

            if(!isValid(uncheckedBlock,block))
            {
                unchecked.remove(uncheckedLocation);
            }
            else
            {
                confirmed.add(uncheckedLocation);

                for (BlockFace face : FACES)
                {
                    Block candidate = uncheckedBlock.getRelative(face);

                    if (isValid(candidate, block) && !isUnchecked(candidate) && !isConfirmed(candidate))
                        unchecked.add(candidate.getLocation());
                }

                unchecked.remove(0);
            }
        }
        return confirmed.size();
    }

    // the block to check must be of the same material
    // and must have one face exposed to transparent block
    private static boolean isValid(Block toCheck, Block block)
    {
        if(!BlockUtil.compare(toCheck, block))
            return false;

        for(BlockFace face : FACES)
        {
            Block relative = block.getRelative(face);
            if (relative.getType().isTransparent() || !relative.getType().isOccluding()) {
                return true;
            }
        }
        return false;
    }

    // Test if the location is in the unchecked list
    private static boolean isUnchecked(Location location)
    {
        for(Location uncheckedLocation : unchecked) {
            if(uncheckedLocation.equals(location))
                return true;
        }
        return false;
    }

    // Test if the block is in the unchecked list
    private static boolean isUnchecked(Block block)
    {
        return isUnchecked(block.getLocation());
    }

    // Test if the block is in the confirmed list
    private static boolean isConfirmed(Location location)
    {
        for(Location confirmedLocation : confirmed) {
            if(confirmedLocation.equals(location))
                return true;
        }
        return false;
    }

    // Test if the block is in the confirmed list
    private static boolean isConfirmed(Block block)
    {
        return isConfirmed(block.getLocation());
    }
}
