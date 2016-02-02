package com.github.hexosse.pluginframework.itemapi.nbt;

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


import org.bukkit.inventory.ItemStack;

/**
 * This file is part of BasePlugin
 */
public class NBTItem {

    private ItemStack bukkitItem;

    public NBTItem(ItemStack Item) {
        bukkitItem = Item.clone();
    }

    public ItemStack getItem() {
        return bukkitItem;
    }

    public void set(String Tag){
        bukkitItem = NBTReflectionutil.set(bukkitItem, Tag);
    }

    public void setString(String Key, String Text){
        bukkitItem = NBTReflectionutil.setString(bukkitItem, Key, Text);
    }

    public String getString(String Key){
        return NBTReflectionutil.getString(bukkitItem, Key);
    }

    public void setInteger(String key, Integer Int){
        bukkitItem = NBTReflectionutil.setInt(bukkitItem, key, Int);
    }

    public Integer getInteger(String key){
        return NBTReflectionutil.getInt(bukkitItem, key);
    }

    public void setDouble(String key, Double d){
        bukkitItem = NBTReflectionutil.setDouble(bukkitItem, key, d);
    }

    public Double getDouble(String key){
        return NBTReflectionutil.getDouble(bukkitItem, key);
    }

    public void setBoolean(String key, Boolean b){
        bukkitItem = NBTReflectionutil.setBoolean(bukkitItem, key, b);
    }

    public Boolean getBoolean(String key){
        return NBTReflectionutil.getBoolean(bukkitItem, key);
    }

    public Boolean hasKey(String key){
        return NBTReflectionutil.hasKey(bukkitItem, key);
    }



    public static ItemStack setString(ItemStack itemStack, String Key, String Text){
        return NBTReflectionutil.setString(itemStack, Key, Text);
    }

    public static String getString(ItemStack itemStack, String Key){
        return NBTReflectionutil.getString(itemStack, Key);
    }

    public static void setInteger(ItemStack itemStack, String key, Integer Int){
        itemStack = NBTReflectionutil.setInt(itemStack, key, Int);
    }

    public static Integer getInteger(ItemStack itemStack, String key){
        return NBTReflectionutil.getInt(itemStack, key);
    }

    public static void setDouble(ItemStack itemStack, String key, Double d){
        itemStack = NBTReflectionutil.setDouble(itemStack, key, d);
    }

    public static Double getDouble(ItemStack itemStack, String key){
        return NBTReflectionutil.getDouble(itemStack, key);
    }

    public static void setBoolean(ItemStack itemStack, String key, Boolean b){
        itemStack = NBTReflectionutil.setBoolean(itemStack, key, b);
    }

    public static Boolean getBoolean(ItemStack itemStack, String key){
        return NBTReflectionutil.getBoolean(itemStack, key);
    }

    public static Boolean hasKey(ItemStack itemStack, String key){
        return NBTReflectionutil.hasKey(itemStack, key);
    }

}

