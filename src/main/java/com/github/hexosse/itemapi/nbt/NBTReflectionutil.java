package com.github.hexosse.itemapi.nbt;

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
 * This file is part of HexocubeItems
 */
public final class NBTReflectionutil
{
    // Prevent accidental construction
    private NBTReflectionutil() {}

    private static String Version;
    private static Class<?> ItemStack = getCraftClass("ItemStack");
    private static Class<?> CraftItemStack = getBukkitClass("inventory.CraftItemStack");
    private static Class<?> NBTTagCompound = getCraftClass("NBTTagCompound");
    private static Class<?> NBTTagList = getCraftClass("NBTTagList");
    private static Class<?> NBTBase = getCraftClass("NBTBase");


    private static String getVersion()
    {
        if(Version != null) return Version;
        Version = Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
        return Version;
    }

    private static Class<?> getBukkitClass(String ClassName)
    {
        try{
            return Class.forName("org.bukkit.craftbukkit." + getVersion() + "." + ClassName);
        }catch(Exception ex){
            System.out.println("Error in NBTItemAPI! (Outdated ?)");
            ex.printStackTrace();
            return null;
        }
    }

    private static Class<?> getCraftClass(String ClassName)
    {
        try{
            return Class.forName("net.minecraft.server." + getVersion() + "." + ClassName);
        }catch(Exception ex){
            System.out.println("Error in NBTItemAPI! (Outdated ?)");
            ex.printStackTrace();
            return null;
        }
    }





    @SuppressWarnings("unchecked")
    private static Object asNMSCopy(ItemStack itemStack)
    {
        java.lang.reflect.Method method;
        try {
            method = CraftItemStack.getMethod("asNMSCopy", ItemStack.class);
            return method.invoke(CraftItemStack, itemStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({ "unchecked" })
    private static ItemStack asBukkitCopy(Object itemStack)
    {
        java.lang.reflect.Method method;
        try {
            method = CraftItemStack.getMethod("asBukkitCopy", itemStack.getClass());
            Object answer = method.invoke(CraftItemStack, itemStack);
            return (ItemStack) answer;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object newNBTTagCompound()
    {
        try{
            return NBTTagCompound.newInstance();
        }catch(Exception ex){
            System.out.println("Error in ItemNBTAPI! (Outdated plugin?)");
            ex.printStackTrace();
            return null;
        }
    }

    private static Object newNBTTagList()
    {
        try{
            return NBTTagList.newInstance();
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    private static boolean hasTag(Object nmsItemStack)
    {
        @SuppressWarnings("rawtypes")
        Class c = nmsItemStack.getClass();
        try {
            Method method = c.getMethod("hasTag");
            return (boolean)method.invoke(nmsItemStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static Object setTag(Object NBTTag, Object nmsItemStack)
    {
        try{
            Method method = nmsItemStack.getClass().getMethod("setTag", NBTTag.getClass());
            method.invoke(nmsItemStack, NBTTag);
            return nmsItemStack;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings({ "unchecked" })
    private static Object getTag(Object nmsItemStack)
    {
        @SuppressWarnings("rawtypes")
        Class c = nmsItemStack.getClass();
        try {
            Method method = c.getMethod("getTag");
            return method.invoke(nmsItemStack);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static ItemStack set(ItemStack itemStack, String Tag)
    {
        Object nmsItemStack = asNMSCopy(itemStack);
        if(nmsItemStack == null){
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }

        Object nbtTag = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        try{
            Method method = nbtTag.getClass().getMethod("set", String.class, NBTBase);
            method.invoke(nbtTag, Tag, newNBTTagList());
            nmsItemStack = setTag(nbtTag, nmsItemStack);
            return asBukkitCopy(nmsItemStack);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return itemStack;
    }

    public static ItemStack setString(ItemStack itemStack, String key, String Text)
    {
        Object nmsItemStack = asNMSCopy(itemStack);
        if(nmsItemStack == null){
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }

        Object nbtTag = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        try{
            Method method = nbtTag.getClass().getMethod("setString", String.class, String.class);
            method.invoke(nbtTag, key, Text);
            nmsItemStack = setTag(nbtTag, nmsItemStack);
            return asBukkitCopy(nmsItemStack);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return itemStack;
    }

    public static String getString(ItemStack itemStack, String key)
    {
        Object nmsItemStack = asNMSCopy(itemStack);
        if(nmsItemStack == null){
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }

        Object nbtTag = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        try{
            Method method = nbtTag.getClass().getMethod("getString", String.class);
            return (String) method.invoke(nbtTag, key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setInt(ItemStack itemStack, String key, Integer i)
    {
        Object nmsItemStack = asNMSCopy(itemStack);
        if(nmsItemStack == null){
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }

        Object nbtTag = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        try{
            Method method = nbtTag.getClass().getMethod("setInt", String.class, int.class);
            method.invoke(nbtTag, key, i);
            nmsItemStack = setTag(nbtTag, nmsItemStack);
            return asBukkitCopy(nmsItemStack);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return itemStack;
    }

    public static Integer getInt(ItemStack itemStack, String key)
    {
        Object nmsItemStack = asNMSCopy(itemStack);
        if(nmsItemStack == null){
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }

        Object nbtTag = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        try{
            Method method = nbtTag.getClass().getMethod("getInt", String.class);
            return (Integer) method.invoke(nbtTag, key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setDouble(ItemStack itemStack, String key, Double d)
    {
        Object nmsItemStack = asNMSCopy(itemStack);
        if(nmsItemStack == null){
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }

        Object nbtTag = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        try{
            Method method = nbtTag.getClass().getMethod("setDouble", String.class, double.class);
            method.invoke(nbtTag, key, d);
            nmsItemStack = setTag(nbtTag, nmsItemStack);
            return asBukkitCopy(nmsItemStack);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return itemStack;
    }

    public static Double getDouble(ItemStack itemStack, String key)
    {
        Object nmsItemStack = asNMSCopy(itemStack);
        if(nmsItemStack == null){
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }

        Object nbtTag = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        try{
            Method method = nbtTag.getClass().getMethod("getDouble", String.class);
            return (Double) method.invoke(nbtTag, key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static ItemStack setBoolean(ItemStack itemStack, String key, Boolean d)
    {
        Object nmsItemStack = asNMSCopy(itemStack);
        if(nmsItemStack == null){
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }

        Object nbtTag = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        try{
            Method method = nbtTag.getClass().getMethod("setBoolean", String.class, boolean.class);
            method.invoke(nbtTag, key, d);
            nmsItemStack = setTag(nbtTag, nmsItemStack);
            return asBukkitCopy(nmsItemStack);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return itemStack;
    }

    public static Boolean getBoolean(ItemStack itemStack, String key)
    {
        Object nmsItemStack = asNMSCopy(itemStack);
        if(nmsItemStack == null){
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }

        Object nbtTag = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        try{
            Method method = nbtTag.getClass().getMethod("getBoolean", String.class);
            return (Boolean) method.invoke(nbtTag, key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static Boolean hasKey(ItemStack itemStack, String key)
    {
        Object nmsItemStack = asNMSCopy(itemStack);
        if(nmsItemStack == null){
            System.out.println("Got null! (Outdated Plugin?)");
            return null;
        }

        Object nbtTag = hasTag(nmsItemStack) ? getTag(nmsItemStack) : newNBTTagCompound();

        try{
            Method method = nbtTag.getClass().getMethod("hasKey", String.class);
            return (Boolean) method.invoke(nbtTag, key);
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}
