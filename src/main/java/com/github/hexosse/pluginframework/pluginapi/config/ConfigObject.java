package com.github.hexosse.pluginframework.pluginapi.config;

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

import org.bukkit.configuration.ConfigurationSection;
import org.json.simple.parser.ParseException;

import java.lang.reflect.InvocationTargetException;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public interface ConfigObject
{
	// Used by {@Link ConfigFile} when serializing an object
	Object serializeObject(final ConfigFile<?> config);

	// Used by {@Link ConfigFile} to deserialize an object
	void deserializeObject(final ConfigFile<?> config, final Object object) throws ParseException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException;
}
