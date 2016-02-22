package com.github.hexosse.pluginframework.pluginapi.reflexion;

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

import org.bukkit.Bukkit;

import static com.github.hexosse.pluginframework.pluginapi.reflexion.Reflexion.getMethod;

/**
 * This file is part of
 */
public class NMSReflexion
{
    // Prevent accidental construction
    private NMSReflexion() {}

    //
    private static String	version	= getVersion();


    /**
     * @return NMS Version
     */
    public static String getVersion()
    {
        if(version != null) return version;
        version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        return version;
    }

    /**
     * @param className Class name
     * @return The class from net.minecraft.server for the current version
     */
    public static Class<?> getNMSClass(String className)
    {
        try {
            return getNMSClassWithException(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param className Class name
     * @return The class from net.minecraft.server for the current version
     * @throws Exception Execption
     */
    public static Class<?> getNMSClassWithException(String className) throws Exception {
        String fullName = "net.minecraft.server." + getVersion() + "." + className;
        return Class.forName(fullName);
    }

    /**
     * @param className Class name
     * @return The class from org.bukkit.craftbukkit for the current version
     */
    public static Class<?> getBukkitClass(String className)
    {
        try {
            return getBukkitClassWithException(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param className Class name
     * @return The class from org.bukkit.craftbukkit for the current version
     * @throws Exception Execption
     */
    public static Class<?> getBukkitClassWithException(String className) throws Exception {
        String fullName = "org.bukkit.craftbukkit." + getVersion() + "." + className;
        return Class.forName(fullName);
    }

    public static Object getHandle(Object obj) {
        try {
            return getMethod(obj.getClass(), "getHandle").invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
