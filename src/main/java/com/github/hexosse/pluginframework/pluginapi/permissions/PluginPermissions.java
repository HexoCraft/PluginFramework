package com.github.hexosse.pluginframework.pluginapi.permissions;

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

import com.github.hexosse.pluginframework.pluginapi.Plugin;
import com.github.hexosse.pluginframework.pluginapi.PluginObject;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * This file is part Plugin
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class PluginPermissions<PluginClass extends Plugin> extends PluginObject<PluginClass>
{
    /**
     * @param plugin The plugin that this object belong to.
     * @param permissionHolder The class holding all of the {@link PluginPermission} objects.
     */
    public PluginPermissions(PluginClass plugin, Class<?> permissionHolder) {
        super(plugin);
        registerPermissions(permissionHolder);
    }

    /**
     * @param sender The sender on which to test the permission
     * @param permission The permission to test
     * @return true if the sender has the permission
     */
    public static boolean has(CommandSender sender, PluginPermission permission)
    {
        return permission.has(sender);
    }

    /**
     * Registers the default permissions for a plugin
     *
     * @param permissionHolder The class holding all of the {@link PluginPermission} objects.
     */
    private void registerPermissions(Class<?> permissionHolder)
    {
        ArrayList<PluginPermission> perms = new ArrayList<PluginPermission>();

        for (Field field : permissionHolder.getDeclaredFields())
        {
            if (field.getType().equals(PluginPermission.class))
            {
                try {
                    perms.add((PluginPermission) field.get(null));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        this.registerPermissions(perms.toArray(new PluginPermission[perms.size()]));
    }

    /**
     * Registers the default permissions for a plugin
     *
     * @param permissions An array of {@link PluginPermission}s to register.
     */
    private void registerPermissions(PluginPermission[] permissions)
    {
        for (PluginPermission permission : permissions) {
            plugin.getServer().getPluginManager().addPermission(new Permission(permission.getNode(), permission.getDescription(), permission.getDefault()));
        }
    }
}
