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

import com.github.hexosse.pluginframework.pluginapi.Plugin;
import com.github.hexosse.pluginframework.pluginapi.PluginObject;
import com.github.hexosse.pluginframework.pluginapi.config.Location.LocationList;
import com.github.hexosse.pluginframework.pluginapi.reflexion.Reflexion;
import com.google.common.io.Files;
import com.google.common.primitives.Primitives;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ConfigKeyFile<PluginClass extends Plugin> extends PluginObject<PluginClass>
{
	private File configFile;
	private YamlConfiguration yaml;
	private String templateName = null;

	private LinkedHashMap<Field, ConfigKey> configKeys = new LinkedHashMap<Field, ConfigKey>();
	private HashMap<String, String[]> comments = new HashMap<String, String[]>();
	protected static String[] header = null;
	protected static String[] footer = null;


	public ConfigKeyFile(PluginClass plugin, final File configFile)
	{
		this(plugin, configFile, null);
	}

	public ConfigKeyFile(PluginClass plugin, final File configFile, final String templateName)
	{
		super(plugin);

		yaml = new YamlConfiguration();
		this.configFile = configFile.getAbsoluteFile();
		this.templateName = templateName;

		extractConfigData();
	}

	public boolean load()
	{
		try
		{
			// Make sure plugin folder exist
			if(!configFile.getParentFile().exists())
				configFile.getParentFile().mkdirs();

			// Create config file from template
			if(!configFile.exists() && templateName!=null)
				createFromTemplate();

			// Create new config file
			if(!configFile.exists())
				configFile.createNewFile();

			// Loading file
			yaml.load(configFile);

			// load comments from file
			loadComments();

			// Load and update config values
			for(Map.Entry<Field,ConfigKey> entry : this.configKeys.entrySet())
				loadKey(entry.getKey(), entry.getValue());

			// Save config to file
			save();

			return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean save()
	{
		try
		{
			// Save all fields
			for(Map.Entry<Field,ConfigKey> entry : this.configKeys.entrySet())
				saveKey(entry.getValue());

			// Save the file using YamlConfiguration
			yaml.save(configFile);

			// Add comments to the file
			saveComments();

			return true;
		} catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public ConfigKey getKey(ConfigKey<?> configKey)
	{
		for(Map.Entry<Field,ConfigKey> entry : this.configKeys.entrySet())
		{
			if(entry.getValue().getKey().equals(configKey.getKey()))
				return entry.getValue();
		}
		return null;
	}

	/**
	 * Create config file frome template
	 * @throws IOException File exeption
	 */
	private void createFromTemplate() throws IOException
	{
		// Load file from ressources
		InputStream is = ((PluginClass)plugin).getClass().getClassLoader().getResourceAsStream(templateName);
		if(is==null) throw new FileNotFoundException("Could not find template file : " + templateName);

		// Convert to byte[]
		byte[] buffer = new byte[is.available()];
		is.read(buffer);

		// Write template to disk
		Files.write(buffer, this.configFile);
	}

	private void extractConfigData()
	{
		for(Field field : this.getClass().getDeclaredFields())
		{
			if(field.getType().equals(ConfigKey.class))
			{
				final Conf.Comment comment = field.getAnnotation(Conf.Comment.class);

				try {
					Reflexion.setAccessible(field);
					ConfigKey key = (ConfigKey) field.get(null);
					if(comment!=null) key.setComment(comment.value());
					this.configKeys.put(field,key);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private final void loadKey(Field field, ConfigKey<?> configKey) throws IllegalAccessException, NoSuchMethodException, InstantiationException, ParseException, InvocationTargetException, NoSuchFieldException
	{
		final Object configValue = yaml.get(configKey.getKey());

		// Config value does not exist
		if(configValue==null)
		{
			saveKey(configKey);
			return;
		}

		// Update value
		configKey.setValue(deserializeObject(configKey.getValue().getClass(), configValue));

		// Update comment
		configKey.setComment(this.comments.get(configKey.getKey()));
		this.comments.put(configKey.getKey(), configKey.getComment());
	}

	private final void saveKey(ConfigKey key) throws IllegalAccessException
	{
		if(key.isDynamic())
			return;

		yaml.set(key.getKey(), serializeObject(key.getValue() != null ? key.getValue() : key.getDefaultValue()));
	}

	private void loadComments() throws Exception
	{
		String line;
		String trimeLine;

		// Read config file and store each line in an array of string
		ArrayList<String> buffer = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(configFile));
		while((line = reader.readLine()) != null)
			buffer.add(line);
		reader.close();

		// Comment storage
		String fullpath, path, lastPath = ""; int indent = 0;
		final List<String> comments = new ArrayList<String>();
		// Does config file should have header or footer
		boolean hasHeader = this.header != null;
		boolean hasFooter = this.footer != null;
		// just to know if header and footer were found
		boolean foundHeader = false;
		boolean foundFooter = false;

		// Parse each lines to find comments
		for(int i = 0; i < buffer.size(); i++)
		{
			line = buffer.get(i);
			trimeLine = line.trim();
			fullpath = line;

			// Find all comments
			if(!trimeLine.isEmpty() && trimeLine.charAt(0) == '#')
			{
				comments.add(buffer.get(i).trim());
				continue;
			}

			// Skip comment header
			if(line.isEmpty() && comments.size()>0 && hasHeader && !foundHeader)
			{
				foundHeader = true;
				comments.clear();
			}

			// Found comment Path
			path = fullpath.split(":")[0];
			if(!path.isEmpty() && comments.size()>0)
			{
				// Erase last path if necessary
				indent = path.indexOf(path.trim());
				if(indent==0) lastPath = "";

				// Reconstruct full path
				if(!lastPath.isEmpty())
				{
					// Update last path from indent value
					String[] pathParts = lastPath.split("\\.");
					lastPath = "";
					for(int j=0;j<(indent/2);j++)
						lastPath += pathParts[j] + ".";

					// Full path
					path = (lastPath + path.trim()).replaceAll("\'","");
				}

				//
				this.comments.put(path, comments.toArray(new String[comments.size()]));

				// Keep in mind the last path
				lastPath = path;

				// clear comments
				comments.clear();
			}
		}

		// Skip comment footer
		if(comments.size()>0 && hasFooter && !foundFooter)
		{
			foundFooter = true;
			comments.clear();
		}
	}

	private void saveComments() throws IOException
	{
		// Read config file and store each line in an array of string
		ArrayList<String> buffer = new ArrayList<String>();
		BufferedReader reader = new BufferedReader(new FileReader(configFile));
		String line;
		while((line = reader.readLine()) != null)
			buffer.add(line);
		reader.close();

		//
		String fullpath, path, lastPath = ""; int indent = 0; String[] comments;
		BufferedWriter writer = new BufferedWriter(new FileWriter(configFile));

		// Header
		if(header != null && header.length > 0)
		{
			for(int i = 0; i < header.length; i++)
			{
				writer.write(header[i]);
				writer.newLine();
			}
		}


		// Parse each lines to add comments
		for (int i = 0; i < buffer.size(); i++)
		{
			// Existing comment line will be replaced
			if(buffer.get(i).charAt(0)=='#')
				continue;

			// Check if a path exist
			fullpath = buffer.get(i);
			path = fullpath.split(":")[0];
			if(path.isEmpty()) continue;

			// Erase last path if necessary
			indent = path.indexOf(path.trim());
			if(indent==0) lastPath = "";

			// Reconstruct full path
			if(!lastPath.isEmpty())
			{
				// Update last path from indent value
				String[] pathParts = lastPath.split("\\.");
				lastPath = "";
				for(int j=0;j<(indent/2);j++)
					lastPath += pathParts[j] + ".";

				// Full path
				path = (lastPath + path.trim()).replaceAll("\'","");
			}

			// New line between each section
			if(indent==0) writer.newLine();

			// Add comment to the path
			if(this.comments.containsKey(path))
			{
				comments = this.comments.get(path);
				boolean addDiese = comments[0].startsWith("#")==false;
				for (int j = 0; j < comments.length; j++)
				{
					writer.write((indent>0?String.format("%"+indent+"s", ""):"") + (addDiese?"# ":"") + comments[j]);
					writer.newLine();
				}
			}

			// Keep in mind the last path
			lastPath = path;

			// Write the original path
			writer.write(buffer.get(i));
			writer.newLine();
		}

		// Footer
		if(footer!=null && footer.length > 0)
		{
			writer.newLine();

			for(int i = 0; i < footer.length; i++)
			{
				writer.write(footer[i]);
				writer.newLine();
			}
		}

		writer.close();
	}

	private final Object serializeObject(final Object object)
	{
		if(object instanceof String) {
			return object.toString().replace(ChatColor.COLOR_CHAR, '&');
		}
		if(object instanceof Enum) {
			return ((Enum<?>)object).name();
		}
		if(object instanceof Map) {
			String tempSection = "temp_section";
			final ConfigurationSection section = yaml.createSection(tempSection);
			for(final Map.Entry<?, ?> entry : ((Map<?, ?>)object).entrySet()) {
				section.set(entry.getKey().toString(), serializeObject(entry.getValue()));
			}
			yaml.set(tempSection, null);
			return section;
		}
		if(object instanceof List) {
			final List<Object> result = new ArrayList<Object>();
			for(final Object value : (List<?>)object) {
				result.add(serializeObject(value));
			}
			return result;
		}
		if(object instanceof Location) {
			final Location location = (Location)object;
			final JSONObject jsonObject = new JSONObject();
			jsonObject.put("world", location.getWorld().getName());
			jsonObject.put("x", location.getX());
			jsonObject.put("y", location.getY());
			jsonObject.put("z", location.getZ());
			jsonObject.put("yaw", location.getYaw());
			jsonObject.put("pitch", location.getPitch());
			return jsonObject.toJSONString();
		}
		if(object instanceof Vector) {
			final Vector vector = (Vector)object;
			final JSONObject jsonObject = new JSONObject();
			jsonObject.put("x", vector.getX());
			jsonObject.put("y", vector.getY());
			jsonObject.put("z", vector.getZ());
			return jsonObject.toJSONString();
		}
		return object;
	}

	private final Object deserializeObject(final Class<?> aClass, final Object object) throws ParseException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException
	{
		if(aClass.isPrimitive()) {
			return Primitives.wrap(aClass).getMethod("valueOf", String.class).invoke(this, object.toString());
		}

		else if(Primitives.isWrapperType(aClass)) {
			return aClass.getMethod("valueOf", String.class).invoke(this, object.toString());
		}

		else if(aClass.isEnum() || object instanceof Enum<?>) {
			return Enum.valueOf((Class<? extends Enum>) aClass, object.toString());
		}

		else if(Map.class.isAssignableFrom(aClass) || object instanceof Map) {
			final ConfigurationSection section = (ConfigurationSection)object;
			final Map<Object, Object> deserializedMap = new HashMap<Object, Object>();
			for(final String key : section.getKeys(false)) {
				final Object value = section.get(key);
				deserializedMap.put(key, deserializeObject(value.getClass(), value));
			}
			final Object map = aClass.newInstance();
			aClass.getMethod("putAll", Map.class).invoke(map, deserializedMap);
			return map;
		}

		else if(LocationList.class.isAssignableFrom(aClass) || object instanceof LocationList) {
			final LocationList result = new LocationList();
			for(final Object value : (List<Location>)object) {
				result.add((Location)deserializeObject(Location.class, value));
			}
			return result;
		}

		else if(List.class.isAssignableFrom(aClass) || object instanceof List) {
			final List<Object> result = new ArrayList<Object>();
			for(final Object value : (List<?>)object) {
				result.add(deserializeObject(value.getClass(), value));
			}
			return result;
		}

		else if(Location.class.isAssignableFrom(aClass) || object instanceof Location) {
			final JSONObject jsonObject = (JSONObject)new JSONParser().parse(object.toString());
			return new Location(Bukkit.getWorld(jsonObject.get("world").toString()), Double.parseDouble(jsonObject.get("x").toString()), Double.parseDouble(jsonObject.get("y").toString()), Double.parseDouble(jsonObject.get("z").toString()), Float.parseFloat(jsonObject.get("yaw").toString()), Float.parseFloat(jsonObject.get("pitch").toString()));
		}

		else if(Vector.class.isAssignableFrom(aClass) || object instanceof Vector) {
			final JSONObject jsonObject = (JSONObject)new JSONParser().parse(object.toString());
			return new Vector(Double.parseDouble(jsonObject.get("x").toString()), Double.parseDouble(jsonObject.get("y").toString()), Double.parseDouble(jsonObject.get("z").toString()));
		}

		return ChatColor.translateAlternateColorCodes('&', object.toString());
	}
}
