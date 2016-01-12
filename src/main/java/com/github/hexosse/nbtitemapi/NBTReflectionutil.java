package com.github.hexosse.nbtitemapi;

/*
 * Copyright 2015 hexosse
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
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

/**
 * This file is part of BasePlugin
 */
public class NBTReflectionutil
{
    private static Class getCraftItemstack()
    {
        String Version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        try{
            return Class.forName("org.bukkit.craftbukkit." + Version +".inventory.CraftItemStack");
        }catch(Exception ex){
            System.out.println("Error in NBTItemAPI! (Outdated ?)");
            ex.printStackTrace();
            return null;
        }
    }

    private static Object getNewNBTTag()
    {
        String Version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        try{
            Class c = Class.forName("net.minecraft.server." + Version +".NBTTagCompound");
            return c.newInstance();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private static Object setNBTTag(Object NBTTag, Object NMSItem)
    {
        try{
            Method method = NMSItem.getClass().getMethod("setTag", NBTTag.getClass());
            method.invoke(NMSItem, NBTTag);
            return NMSItem;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    private static Object getNMSItemStack(ItemStack item)
    {
        @SuppressWarnings("rawtypes")
        Class cis = getCraftItemstack();
        try {
            Method method = cis.getMethod("asNMSCopy", ItemStack.class);
            return method.invoke(cis, item);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ItemStack getBukkitItemStack(Object item)
    {
        @SuppressWarnings("rawtypes")
        Class cis = getCraftItemstack();
        try {
            Method method = cis.getMethod("asBukkitCopy", item.getClass());
            Object answer = method.invoke(cis, item);
            return (ItemStack) answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object getNBTTagCompound(Object nmsitem)
    {
        @SuppressWarnings("rawtypes")
        Class c = nmsitem.getClass();
        try {
            Method method = c.getMethod("getTag");
            return method.invoke(nmsitem);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ItemStack setString(ItemStack item, String key, String Text)
    {
        Object nmsitem = getNMSItemStack(item);
        if(nmsitem == null){
            System.out.println("Got null! (Outdated ?)");
            return null;
        }
        
        Object nbttag = getNBTTagCompound(nmsitem);
        if(nbttag == null)
            nbttag = getNewNBTTag();
        
        try{
            Method method = nbttag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(nbttag, key, Text);
            nmsitem = setNBTTag(nbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return item;
    }

    public static String getString(ItemStack item, String key)
    {
        Object nmsitem = getNMSItemStack(item);
        if(nmsitem == null){
            System.out.println("Got null! (Outdated ?)");
            return null;
        }
        
        Object nbttag = getNBTTagCompound(nmsitem);
        if(nbttag == null)
            nbttag = getNewNBTTag();
        
        try{
            Method method = nbttag.getClass().getMethod("getString", String.class);
            return (String) method.invoke(nbttag, key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setInt(ItemStack item, String key, Integer i)
    {
        Object nmsitem = getNMSItemStack(item);
        if(nmsitem == null){
            System.out.println("Got null! (Outdated ?)");
            return null;
        }
        
        Object nbttag = getNBTTagCompound(nmsitem);
        if(nbttag == null)
            nbttag = getNewNBTTag();
        
        try{
            Method method = nbttag.getClass().getMethod("setInt", String.class, int.class);
            method.invoke(nbttag, key, i);
            nmsitem = setNBTTag(nbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return item;
    }

    public static Integer getInt(ItemStack item, String key)
    {
        Object nmsitem = getNMSItemStack(item);
        if(nmsitem == null){
            System.out.println("Got null! (Outdated ?)");
            return null;
        }
        
        Object nbttag = getNBTTagCompound(nmsitem);
        if(nbttag == null)
            nbttag = getNewNBTTag();
        
        try{
            Method method = nbttag.getClass().getMethod("getInt", String.class);
            return (Integer) method.invoke(nbttag, key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setDouble(ItemStack item, String key, Double d)
    {
        Object nmsitem = getNMSItemStack(item);
        if(nmsitem == null){
            System.out.println("Got null! (Outdated ?)");
            return null;
        }
        
        Object nbttag = getNBTTagCompound(nmsitem);
        if(nbttag == null)
            nbttag = getNewNBTTag();
        
        try{
            Method method = nbttag.getClass().getMethod("setDouble", String.class, double.class);
            method.invoke(nbttag, key, d);
            nmsitem = setNBTTag(nbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return item;
    }

    public static Double getDouble(ItemStack item, String key)
    {
        Object nmsitem = getNMSItemStack(item);
        if(nmsitem == null){
            System.out.println("Got null! (Outdated ?)");
            return null;
        }
        
        Object nbttag = getNBTTagCompound(nmsitem);
        if(nbttag == null)
            nbttag = getNewNBTTag();
        
        try{
            Method method = nbttag.getClass().getMethod("getDouble", String.class);
            return (Double) method.invoke(nbttag, key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setBoolean(ItemStack item, String key, Boolean d)
    {
        Object nmsitem = getNMSItemStack(item);
        if(nmsitem == null){
            System.out.println("Got null! (Outdated ?)");
            return null;
        }
        
        Object nbttag = getNBTTagCompound(nmsitem);
        if(nbttag == null)
            nbttag = getNewNBTTag();
        
        try{
            Method method = nbttag.getClass().getMethod("setBoolean", String.class, boolean.class);
            method.invoke(nbttag, key, d);
            nmsitem = setNBTTag(nbttag, nmsitem);
            return getBukkitItemStack(nmsitem);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return item;
    }

    public static Boolean getBoolean(ItemStack item, String key)
    {
        Object nmsitem = getNMSItemStack(item);
        if(nmsitem == null){
            System.out.println("Got null! (Outdated ?)");
            return null;
        }
        
        Object nbttag = getNBTTagCompound(nmsitem);
        if(nbttag == null)
            nbttag = getNewNBTTag();
        
        try{
            Method method = nbttag.getClass().getMethod("getBoolean", String.class);
            return (Boolean) method.invoke(nbttag, key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static Boolean hasKey(ItemStack item, String key)
    {
        Object nmsitem = getNMSItemStack(item);
        if(nmsitem == null){
            System.out.println("Got null! (Outdated ?)");
            return null;
        }
        
        Object nbttag = getNBTTagCompound(nmsitem);
        if(nbttag == null)
            nbttag = getNewNBTTag();
        
        try{
            Method method = nbttag.getClass().getMethod("hasKey", String.class);
            return (Boolean) method.invoke(nbttag, key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}

