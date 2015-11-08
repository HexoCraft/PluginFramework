package com.github.hexosse.baseplugin.config;

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

import com.github.hexosse.baseplugin.BaseObject;
import com.github.hexosse.baseplugin.BasePlugin;
import com.github.hexosse.baseplugin.config.Location.LocationList;
import com.google.common.primitives.Primitives;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This file is part of ChestPreview
 *
 * références :
 *      http://stackoverflow.com/questions/14268981/modify-a-class-definitions-annotation-string-parameter-at-runtime
 *
 * @author <b>hexosse</b> (<a href="https://github.com/hexosse">hexosse on GitHub</a>).
 * @version 1.0.0
 */
public class BaseConfig<PluginClass extends BasePlugin> extends BaseObject<PluginClass>
{
    protected final YamlConfiguration yaml;
    protected final File configFile;
    protected String templateName = null;
    HashMap<String, String[]> pathComments = new HashMap<String, String[]>();
    protected String[] header = null;
    protected String[] footer = null;


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ConfigHeader
    {
        // Comment
        public String[] comment() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    protected @interface ConfigComment
    {
        // yaml path
        public String path() default "";

        // Comment
        public String[] comment() default "";
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    protected @interface ConfigOptions
    {
        // yaml path
        public String path() default "";

        // Comment
        public String[] comment() default "";

        // ignored
        public boolean ignored() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    public @interface ConfigFooter
    {
        // Comment
        public String[] comment() default "";
    }


    public BaseConfig(PluginClass plugin, final File configFile)
    {
        super(plugin);
        yaml = new YamlConfiguration();
        this.configFile = configFile.getAbsoluteFile();
        this.templateName = null;
    }

    public BaseConfig(PluginClass plugin, final File configFile, final String templateName)
    {
        super(plugin);
        yaml = new YamlConfiguration();
        this.configFile = configFile.getAbsoluteFile();
        this.templateName = templateName;
    }

    public boolean load()
    {
        try
        {
            // Make sure that plugin folder exist
            // If not create it
            if(!configFile.getParentFile().exists())
                configFile.getParentFile().mkdirs();

            // Create config file from template if it does not exist
            if(!configFile.exists() && templateName!=null)
                createFromTemplate();

            // Create new config file
            if(!configFile.exists())
                configFile.createNewFile();

            // Loading file
            yaml.load(configFile);

            // load comments from file
            loadComments();

            // Update header
            final ConfigHeader configHeader = getClass().getAnnotation(ConfigHeader.class);
            if(configHeader!=null) header = configHeader.comment();

            // Update config values
            for(Field field : getClass().getDeclaredFields())
                loadField(field);

            // Update footer
            final ConfigFooter configFooter = getClass().getAnnotation(ConfigFooter.class);
            if(configFooter!=null) footer = configFooter.comment();

            // Save config to file
            save();

            return true;
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(InvalidConfigurationException e)
        {
            e.printStackTrace();
            return false;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        } catch(IllegalAccessException e)
        {
            e.printStackTrace();
            return false;
        } catch(NoSuchMethodException e)
        {
            e.printStackTrace();
            return false;
        } catch(InstantiationException e)
        {
            e.printStackTrace();
            return false;
        } catch(ParseException e)
        {
            e.printStackTrace();
            return false;
        } catch(InvocationTargetException e)
        {
            e.printStackTrace();
            return false;
        } catch(NoSuchFieldException e)
        {
            e.printStackTrace();
            return false;
        } catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean save()
    {
        try
        {
            // Save all fields
            for(Field field : getClass().getDeclaredFields())
            {
                final ConfigOptions configOption = field.getAnnotation(ConfigOptions.class);
                if(configOption!=null)
                    saveField(field, configOption.path());
            }

            // Save the file using YamlConfiguration
            yaml.save(configFile);

            // Add comments to the file
            saveComments();

            return true;
        } catch(IOException | IllegalAccessException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    private final void loadField(final Field field) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException, InstantiationException
    {
        if(doSkipField(field)) return;

        final ConfigComment configComment = field.getAnnotation(ConfigComment.class);
        if(configComment!=null && pathComments.containsKey(configComment.path())==false && configComment.comment().length>1)
            pathComments.put(configComment.path(), configComment.comment());

        final ConfigOptions configOption = field.getAnnotation(ConfigOptions.class);
        if(configOption==null) return;

        final Object configValue = yaml.get(configOption.path());

        // Config value does not exist
        if(configValue==null)
            saveField(field, configOption.path());

        // Update fieldAccessor
        else
            field.set(this, deserializeObject(field.getType(), configValue));

        // Commentaires
        if(configOption!=null && pathComments.containsKey(configOption.path())==false && configOption.comment().length>1)
            pathComments.put(configOption.path(),configOption.comment());
    }

    private final void saveField(final Field field, String path) throws IllegalAccessException
    {
        if(doSkipField(field)) return;

        yaml.set(path, serializeObject(field.get(this)));
    }

    /**
     * Create config file frome template
     * @throws IOException File exeption
     */
    private void createFromTemplate() throws IOException
    {
        InputStream is = null;

        // Load file from ressources
        is = ((PluginClass)plugin).getClass().getClassLoader().getResourceAsStream(templateName);
        if(is==null)
            throw new FileNotFoundException("Could not find template file : " + templateName);

        // Write template to disk
        copyInputStreamToFile(is,configFile);
        is.close();
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
        boolean hasHeader = getClass().getAnnotation(ConfigHeader.class) != null;
        boolean hasFooter = getClass().getAnnotation(ConfigFooter.class) != null;
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
            if(!line.isEmpty() && trimeLine.charAt(0)=='#')
            {
                comments.add(buffer.get(i).trim());
                continue;
            }

            // Found comment Header
            if(line.isEmpty() && comments.size()>0 && hasHeader && !foundHeader)
            {
                final ConfigHeader configHeader = getClass().getAnnotation(ConfigHeader.class);
                if(configHeader!=null)
                    changeAnnotationValue(configHeader, "comment", comments.toArray(new String[comments.size()]));

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
                ConfigComment configComment = getConfigComment(path);
                if(configComment != null)
                    pathComments.put(configComment.path(), comments.toArray(new String[comments.size()]));

                //
                ConfigOptions configOptions = getConfigOptions(path);
                if(configOptions != null)
                    pathComments.put(configOptions.path(), comments.toArray(new String[comments.size()]));

                // Keep in mind the last path
                lastPath = path;

                // clear comments
                comments.clear();
            }
        }

        // Found comment Footer
        if(comments.size()>0 && hasFooter && !foundFooter)
        {
            final ConfigFooter configFooter = getClass().getAnnotation(ConfigFooter.class);
            if(configFooter!=null)
                changeAnnotationValue(configFooter, "comment", comments.toArray(new String[comments.size()]));

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
        if(header!=null)
        {
            for(int i = 0; i < header.length; i++)
            {
                writer.write(header[i]);
                writer.newLine();
            }
            if(header.length > 0)
            {
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
            if(pathComments.containsKey(path))
            {
                comments = pathComments.get(path);
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
        if(footer!=null)
        {
            if(footer.length > 0)
            {
                writer.newLine();
                writer.newLine();
            }
            for(int i = 0; i < footer.length; i++)
            {
                writer.write(footer[i]);
                writer.newLine();
            }
        }

        writer.close();
    }

    /**
     * Copy InputStream to file
     *
     * @param initialStream The InputStream to copy to file
     * @param file The file wich will contain the InputStream
     */
    private void copyInputStreamToFile(InputStream initialStream, File file)
    {
        try
        {
            OutputStream outStream = new FileOutputStream(file);
            byte[] buffer = new byte[8 * 1024];
            int bytesRead;
            while ((bytesRead = initialStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            outStream.close();
            initialStream.close();

        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private boolean doSkipField(Field field)
    {
        final ConfigOptions options = field.getAnnotation(ConfigOptions.class);

        return Modifier.isTransient(field.getModifiers())
                //|| Modifier.isStatic(field.getModifiers())
                //|| Modifier.isFinal(field.getModifiers())
                || (options!=null && options.ignored());
    }

    /**
     * Changes the annotation value for the given key of the given annotation to newValue and returns
     * the previous value.
     */
    @SuppressWarnings("unchecked")
    private static Object changeAnnotationValue(Annotation annotation, String key, Object newValue)
    {
        // Get the field
        Object handler = Proxy.getInvocationHandler(annotation);
        Field field;
        Map<String, Object> memberValues;
        try {
            field = handler.getClass().getDeclaredField("memberValues");
            field.setAccessible(true);
            memberValues = (Map<String, Object>) field.get(handler);
        } catch(NoSuchFieldException | SecurityException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }

        // Récupère l'ancienne valeur
        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }

        // Nouvelle valeur
        memberValues.put(key, newValue);

        // Mise à jour du field
        try
        {
            if(Modifier.isFinal(field.getModifiers()))
            {
                Field modifiers = null;
                modifiers = Field.class.getDeclaredField("modifiers");
                modifiers.setAccessible(true);

                try {
                    modifiers.set(field, field.getModifiers() & ~Modifier.FINAL);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

             field.set(handler, memberValues);

        } catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return oldValue;
    }


    private Field getField(String path)
    {
        for(Field field : getClass().getDeclaredFields())
        {
            ConfigComment configComment = field.getAnnotation(ConfigComment.class);
            if(configComment!=null && configComment.path().equals(path))
                return field;

            ConfigOptions configOptions = field.getAnnotation(ConfigOptions.class);
            if(configOptions!=null && configOptions.path().equals(path))
                return field;
        }
        return null;
    }

    private ConfigComment getConfigComment(String path)
    {
        for(Field field : getClass().getDeclaredFields())
        {
            ConfigComment configComment = field.getAnnotation(ConfigComment.class);
            if(configComment!=null && configComment.path().equals(path))
                return configComment;
        }
        return null;
    }

    private ConfigOptions getConfigOptions(String path)
    {
        for(Field field : getClass().getDeclaredFields())
        {
            ConfigOptions option = field.getAnnotation(ConfigOptions.class);
            if(option!=null && option.path().equals(path))
                return option;
        }
        return null;
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
