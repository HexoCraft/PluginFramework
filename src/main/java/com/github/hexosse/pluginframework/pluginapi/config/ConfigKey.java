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

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ConfigKey<T>
{
	private String key;				// Key (path)
	private T defaultValue;			// Default value of the key
	private T value;				// value of the key from config file
	private String[] comment;		// Comments of the key
	private boolean dynamic;		// Dynamic key are not saved to file

	public ConfigKey(String key, T defaultValue, String[] comment, boolean dynamic)
	{
		this.key = key;
		this.defaultValue = defaultValue;
		this.value = defaultValue;
		this.comment = comment;
		this.dynamic = dynamic;
	}

	public ConfigKey(String key, T defaultValue, String[] comment)
	{
		this(key, defaultValue, comment, false);
	}

	public ConfigKey(String key, T defaultValue, String comment)
	{
		this(key, defaultValue, new String[] {comment}, false);
	}

	public ConfigKey(String key, T defaultValue)
	{
		this(key, defaultValue, new String[] {""}, false);
	}

	public String getKey()
	{
		return this.key;
	}

	public T getDefaultValue()
	{
		return this.defaultValue;
	}

	public T getValue()
	{
		return this.value;
	}

	public void setValue(Object value)
	{
		this.value = (T)value;
	}

	public String[] getComment()
	{
		return this.comment;
	}

	public void setComment(String[] comment)
	{
		if(comment!=null && comment.length!=0)
			this.comment = comment;
	}

	public boolean isDynamic()
	{
		return this.dynamic;
	}
}
