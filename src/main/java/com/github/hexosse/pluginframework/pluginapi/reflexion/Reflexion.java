package com.github.hexosse.pluginframework.pluginapi.reflexion;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * This file is part of HexocubeItems
 */
public class Reflexion
{
    // Prevent accidental construction
    private Reflexion() {}


    /**
     * @param clazz Class containing th constructor
     * @param parameterTypes the list of parameters
     *
     * @return the {@code Constructor<?>} that matches the specified
     * {@code clazz}  and {@code parameterTypes}
     */
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes)
    {
        try {
            return getConstructorWithExecption(clazz, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param clazz Class containing th constructor
     * @param parameterTypes the list of parameters
     *
     * @return the {@code Constructor<?>} that matches the specified
     * {@code clazz}  and {@code parameterTypes}
     *
     * @exception NoSuchMethodException if a matching method is not found
     *            or if the name is "&lt;init&gt;"or "&lt;clinit&gt;".
     */
    public static Constructor<?> getConstructorWithExecption(Class<?> clazz, Class<?>... parameterTypes) throws Exception
    {
        Constructor<?> constructor = clazz.getConstructor(parameterTypes);
        if(constructor!=null) {
            constructor.setAccessible(true);
            return constructor;
        }

        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(parameterTypes);
        if(declaredConstructor!=null) {
            declaredConstructor.setAccessible(true);
            return declaredConstructor;
        }

        throw new NoSuchMethodException(clazz.getName());
    }

    /**
     * @param clazz Class that should contain the method
     * @param name the name of the method
     * @param parameterTypes the list of parameters
     *
     * @return the {@code Method} object that matches the specified
     * {@code clazz} and {@code name} and {@code parameterTypes}
     */
    public static Method getMethod(Class<?> clazz, String name, Class<?>... parameterTypes)
    {
        try {
            return getMethodWithExecption(clazz, name, parameterTypes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param clazz Class that should contain the method
     * @param name the name of the method
     * @param parameterTypes the list of parameters
     *
     * @return the {@code Method} object that matches the specified
     * {@code clazz} and {@code name} and {@code parameterTypes}
     *
     * @exception NoSuchMethodException if a matching method is not found
     *            or if the name is "&lt;init&gt;"or "&lt;clinit&gt;".
     */
    public static Method getMethodWithExecption(Class<?> clazz, String name, Class<?>... parameterTypes) throws Exception
    {
        Method method = clazz.getMethod(name, parameterTypes);
        if(method!=null) {
            method.setAccessible(true);
            return method;
        }

        Method declaredMethod = clazz.getDeclaredMethod(name, parameterTypes);
        if(declaredMethod!=null) {
            declaredMethod.setAccessible(true);
            return declaredMethod;
        }

        throw new NoSuchMethodException(clazz.getName() + "." + name);
    }

    /**
     * @param clazz Class that should contain the method
     * @param name the name of the method
     *
     * @return the {@code Method} object that matches the specified
     * {@code clazz} and {@code name} and {@code parameterTypes}
     */
    public static Field getField(Class<?> clazz, String name)
    {
        try {
            return getFieldWithExecption(clazz, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param clazz Class that should contain the field
     * @param name the name of the filed
     *
     * @return the {@code Field} that matches the specified
     * {@code clazz} and {@code name}
     *
     * @exception NoSuchMethodException if a matching method is not found
     *            or if the name is "&lt;init&gt;"or "&lt;clinit&gt;".
     */
    public static Field getFieldWithExecption(Class<?> clazz, String name) throws Exception
    {
        Field field = clazz.getField(name);
        if(field!=null) {
            setAccessible(field);
            return field;
        }

        Field declaredField = clazz.getDeclaredField(name);
        if(declaredField!=null) {
            setAccessible(declaredField);
            return declaredField;
        }

        throw new NoSuchMethodException(clazz.getName() + "." + name);
    }

    /**
     * @param field The fiel that should bze accessible
     * @return <code>true</code> if the field was previously accessible
     */
     public static Field setAccessible(Field field) throws NoSuchFieldException, IllegalAccessException
     {
        field.setAccessible(true);
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        return field;
    }}
