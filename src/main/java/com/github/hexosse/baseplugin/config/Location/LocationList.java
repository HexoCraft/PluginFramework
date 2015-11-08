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

package com.github.hexosse.baseplugin.config.Location;

import com.github.hexosse.baseplugin.config.BaseConfig;
import org.bukkit.Location;

import java.util.ArrayList;

/**
 * The LocationList can be used within {@link BaseConfig} to store location in file
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public class LocationList extends ArrayList<Location>
{
    /**
     * @param location The location to add to the list
     * @return true if the location does not already exist in the list
     */
    @Override
    public boolean add(Location location)
    {
        if (!this.contains(location)){
            return super.add(location);
        }
        return false;
    }

    /**
     * @param location The location to remove from the list
     * @return true if the location is removed
     */
    public boolean remove(Location location)
    {
        return super.remove(location);
    }
}
