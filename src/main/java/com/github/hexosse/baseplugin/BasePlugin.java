package com.github.hexosse.baseplugin;

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

import com.github.hexosse.baseplugin.logging.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The base class that the main plugin class should extend.
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 */
public abstract class BasePlugin extends JavaPlugin
{
    /**
     * The logger used by the plugin
     */
    protected PluginLogger pluginLogger = new PluginLogger(this);


    /**
     * @return The logger used by the plugin
     */
    public PluginLogger getPluginLogger() {
        return pluginLogger;
    }
}
