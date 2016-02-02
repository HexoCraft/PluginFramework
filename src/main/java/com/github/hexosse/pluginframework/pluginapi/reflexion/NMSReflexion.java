package com.github.hexosse.pluginframework.pluginapi.reflexion;

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
