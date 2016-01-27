package com.github.hexosse.itemapi.event;

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

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.lang.reflect.Method;

/**
 * This file is part of BasePlugin
 */
public class PlayerEvents
{
    public static String onHit(Class<?> c, EntityDamageByEntityEvent event)
    {
        try {
            Object o = c.newInstance();
            Method method = c.getDeclaredMethod("onHit", EntityDamageByEntityEvent.class);
            String ret = (String) method.invoke(o, event);
            return ret;

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String onBlockBreak(Class<?> c, BlockBreakEvent event)
    {
        try {
            Object o = c.newInstance();
            Method method = c.getDeclaredMethod("onBlockBreak", BlockBreakEvent.class);
            String ret = (String) method.invoke(o, event);
            return ret;

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String onConsume(Class<?> c, PlayerItemConsumeEvent event)
    {
        try {
            Object o = c.newInstance();
            Method method = c.getDeclaredMethod("onConsume", PlayerItemConsumeEvent.class);
            String ret = (String) method.invoke(o, event);
            return ret;

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String onRightClick(Class<?> c, PlayerInteractEvent event)
    {
        try {
            Object o = c.newInstance();
            Method method = c.getDeclaredMethod("onRightClick", PlayerInteractEvent.class);
            String ret = (String) method.invoke(o, event);
            return ret;

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String onLeftClick(Class<?> c, PlayerInteractEvent event) {
        try {
            Object o = c.newInstance();
            Method method = c.getDeclaredMethod("onLeftClick", PlayerInteractEvent.class);
            String ret = (String) method.invoke(o, event);
            return ret;

        } catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}

