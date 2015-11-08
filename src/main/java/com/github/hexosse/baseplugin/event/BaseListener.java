package com.github.hexosse.baseplugin.event;

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

import com.github.hexosse.baseplugin.BaseObject;
import com.github.hexosse.baseplugin.BasePlugin;
import org.bukkit.event.Listener;

/**
 * The class that all listener object belonging to this plugin should extend
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public abstract class BaseListener<PluginClass extends BasePlugin> extends BaseObject<PluginClass> implements Listener
{
    /**
     * @param plugin The plugin that this listener belongs to.
     */
    public BaseListener(PluginClass plugin)
    {
        super(plugin);
    }
}