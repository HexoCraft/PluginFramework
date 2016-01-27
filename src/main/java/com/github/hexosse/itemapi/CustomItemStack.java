package com.github.hexosse.itemapi;

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

import com.github.hexosse.itemapi.nbt.NBTItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This file is part of ItemApi
 */

public abstract class CustomItemStack
{
    // Tag containing the name of the custom item
    public static String customNameTag = "customName";
    // Itemstack used to store the custom data
    private ItemStack item;


    /**
     * Static function used to create CustomItemStack
     *
     * @param aClass Class that inherit from CustomItemStack
     * @return CustomItemStack
     */
    static public ItemStack create(Class<? extends CustomItemStack> aClass)
    {
        return create(aClass, 1);
    }

    /**
     * Static function used to create CustomItemStack
     *
     * @param aClass Class that inherit from CustomItemStack
     * @param amount Amount of item in the stack
     * @return CustomItemStack
     */
    static public ItemStack create(Class<? extends CustomItemStack> aClass, int amount)
    {
        try {
            ItemStack item = aClass.newInstance().getItem();
            item.setAmount(amount);
            return item;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * CustomItemStack Constructor
     *
     * @param type Material used for the custom item
     */
    protected CustomItemStack(Material type)  {
        this(type, 1);
    }

    /**
     * CustomItemStack Constructor
     *
     * @param type Material used for the custom item
     * @param amount Amount fo items in the stack
     */
    protected CustomItemStack(Material type, int amount)
    {
        this.item = new ItemStack(type, amount);

        NBTItem nbti = new NBTItem(this.item);
        nbti.setString(this.customNameTag, this.getCustomName());

        this.item = nbti.getItem();
    }

    /**
     * @return Custom name of the custom item
     */
    public String getCustomName() {
        return getClass().getSimpleName();
    }

    /**
     * @return Itemstack
     */
    public ItemStack getItem() {
        return this.item;
    }



    /**
     * @param itemStack ItemStack to check
     * @return true it the ItemStack is a CustomItemStack
     */
    static public boolean isCustomItemStack(ItemStack itemStack)
    {
        if(itemStack.getType() == Material.AIR ) return false;
        return getCustomName(itemStack).isEmpty()==false;
    }

    /**
     * @param itemStack ItemStack to check
     * @return true it the ItemStack is a CustomItemStack
     */
    static public boolean isCustomItemStack(ItemStack itemStack, Class<? extends CustomItemStack> aClass)
    {
        if(itemStack.getType() == Material.AIR ) return false;

        return getCustomName(itemStack).equals(aClass.getSimpleName());
    }

    /**
     * @param itemStack item stack
     * @return Custom name of the custom item
     */
    static public String getCustomName(ItemStack itemStack) {
        return NBTItem.getString(itemStack, CustomItemStack.customNameTag);
    }



    /**
     * Sets default display name of item
     *
     * @param name Name of the item
     */
    public void setDisplayName(String name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
    }

    /**
     * @return default display name of item
     */
    public String getDisplayName() {
        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta.getDisplayName();
    }

    /**
     * Sets lore of item
     * @param lore Lore
     */
    public void setLore(String lore) {
        ArrayList<String> aLore = new ArrayList<String>();
        aLore.add(lore);
        setLore(aLore);
    }

    /**
     * Sets lore of item
     * @param lore Lore
     */
    public void setLore(String... lore) {
        List<String> list = new ArrayList<String>();
        Collections.addAll(list, lore);
        setLore(list);
    }

    /**
     * Sets lore of item
     * @param lore Lore
     */
    public void setLore(List<String> lore) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
    }

    /**
     * Add lore to existing lore
     * @param lore lore to add
     */
    public void addLore(String lore) {
        List<String> aLore = (getLore()!=null ? getLore() : new ArrayList<String>());
        aLore.add(lore);
        setLore(aLore);
    }

    /**
     * Add lore to existing lore
     * @param lore lore to add
     */
    public void addLore(String... lore) {
        List<String> aLore = (getLore()!=null ? getLore() : new ArrayList<String>());
        Collections.addAll(aLore, lore);
        setLore(aLore);
    }

    /**
     *
     * @return lore of item, null if it's not set
     */
    public List<String> getLore() {
        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta.getLore();
    }

    /**
     * Adds the specified enchantment to this item meta.
     *
     * @param enchantment Enchantment to add
     * @param level Level for the enchantment
     * @return true if the item meta changed as a result of this call, false otherwise
     */
    public boolean addEnchant(Enchantment enchantment, int level) {
        ItemMeta itemMeta = item.getItemMeta();
        if(!itemMeta.addEnchant(enchantment, level, true))
            return false;
        item.setItemMeta(itemMeta);
        return true;
    }

    /**
     * Removes the specified enchantment from this item meta.
     *
     * @param enchantment Enchantment to remove
     * @return true if the item meta changed as a result of this call, false
     *     otherwise
     */
    public boolean removeEnchant(Enchantment enchantment) {
        ItemMeta itemMeta = item.getItemMeta();
        if(!itemMeta.removeEnchant(enchantment))
            return false;
        item.setItemMeta(itemMeta);
        return true;
    }

    /**
     * Adds a glowing effect.
     */
    public void addGlow() {
        NBTItem nbti = new NBTItem(this.item);
        nbti.set("ench");
        this.item = nbti.getItem();
    }
}
