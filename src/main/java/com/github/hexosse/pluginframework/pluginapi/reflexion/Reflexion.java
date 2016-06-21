package com.github.hexosse.pluginframework.pluginapi.reflexion;

/*
 * Copyright 2016 Hexosse
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
		try
		{
			Constructor<?> constructor = clazz.getConstructor(parameterTypes);
			if(constructor!=null) {
				return constructor;
			}
		}
		catch(NoSuchMethodException e)
		{
			try
			{
				Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(parameterTypes);
				if(declaredConstructor!=null) {
					declaredConstructor.setAccessible(true);
					return declaredConstructor;
				}
			}
			catch(NoSuchMethodException e1)
			{
				return null;
			}
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
    public static Constructor<?> getConstructorWithException(Class<?> clazz, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
    {
        Constructor<?> constructor = clazz.getConstructor(parameterTypes);
        if(constructor!=null) {
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
		try
		{
			Method declaredMethod = clazz.getDeclaredMethod(name, parameterTypes);
			if(declaredMethod!=null) {
				declaredMethod.setAccessible(true);
				return declaredMethod;
			}
		}
		catch(NoSuchMethodException ignored) {}

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
	public static Method getDeclaredMethodWithException(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
	{
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
	 * @param parameterTypes the list of parameters
	 *
	 * @return the {@code Method} object that matches the specified
	 * {@code clazz} and {@code name} and {@code parameterTypes}
	 *
	 * @exception NoSuchMethodException if a matching method is not found
	 *            or if the name is "&lt;init&gt;"or "&lt;clinit&gt;".
	 */
	public static Method getMethodWithException(Class<?> clazz, String name, Class<?>... parameterTypes) throws NoSuchMethodException, SecurityException
	{
		Method method = clazz.getMethod(name, parameterTypes);
		if(method!=null) {
			method.setAccessible(true);
			return method;
		}

		throw new NoSuchMethodException(clazz.getName() + "." + name);
	}




	/**
	 * @param clazz Class that should contain the method
	 * @param fieldName the name of the method
	 *
	 * @return the {@code Method} object that matches the specified
	 * {@code clazz} and {@code name} and {@code parameterTypes}
	 */
	public static Field getField(Class<?> clazz, String fieldName)
	{
		try
		{
			Field declaredField = clazz.getDeclaredField(fieldName);
			if(declaredField!=null && !declaredField.isAccessible()) {
				setAccessible(declaredField);
				return declaredField;
			}
		}
		catch(NoSuchFieldException ignored) {}
		catch(IllegalAccessException ignored) {}

		return null;
	}

	/**
	 * @param clazz Class that should contain the field
	 * @param fieldName the name of the filed
	 *
	 * @return the {@code Field} that matches the specified
	 * {@code clazz} and {@code name}
	 *
	 * @exception NoSuchMethodException if a matching method is not found
	 *            or if the name is "&lt;init&gt;"or "&lt;clinit&gt;".
	 */
	public static Field getDeclaredFieldWithException(Class<?> clazz, String fieldName) throws NoSuchFieldException, SecurityException, IllegalAccessException
	{
		Field declaredField = clazz.getDeclaredField(fieldName);
		if(declaredField!=null && !declaredField.isAccessible()) {
			setAccessible(declaredField);
			return declaredField;
		}

		throw new NoSuchFieldException(fieldName);
	}

    /**
     * @param clazz Class that should contain the field
     * @param fieldName the name of the filed
     *
     * @return the {@code Field} that matches the specified
     * {@code clazz} and {@code name}
     *
     * @exception NoSuchMethodException if a matching method is not found
     *            or if the name is "&lt;init&gt;"or "&lt;clinit&gt;".
     */
    public static Field getFieldWithException(Class<?> clazz, String fieldName) throws NoSuchFieldException, SecurityException, IllegalAccessException
	{
        Field field = clazz.getField(fieldName);
        if(field!=null && !field.isAccessible()) {
            setAccessible(field);
            return field;
        }

        throw new NoSuchFieldException(fieldName);
    }


	public static Object getField(Class<?> clazz, String fieldName, Object from)
	{
		try
		{
			return getField(clazz, fieldName).get(from);
		}
		catch(IllegalAccessException e)
		{
			return null;
		}
	}

	/**
	 * @param clazz Class that should contain the method
	 * @param fieldName the name of the method
	 *
	 * @return the {@code Method} object that matches the specified
	 * {@code clazz} and {@code name} and {@code parameterTypes}
	 */
	public static <T> T getField(Class<?> clazz, String fieldName, Class<T> fieldType, Object from) throws NoSuchFieldException, SecurityException, IllegalAccessException
	{
		Object object = getField(clazz, fieldName, from);
		return fieldType.cast(object);
	}

    /**
     * Returns a field value from any class.
     * Note: This ignores security levels defined by the parent class.
     * @param from
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> T getField(Object from, String fieldName) throws NoSuchFieldException
	{
        // get class
        Class<?> clazz = from.getClass ();

        // check the whole class tree
        do {
            try
            {
                // get the field of this value
				Field field = getField(clazz, fieldName);
				if(field!=null)
                    return (T)field.get(from);
            } catch(IllegalAccessException  ignored) {}
		} while (clazz.getSuperclass()!=Object.class && ((clazz = clazz.getSuperclass())!=null));

        // in case of failure
		throw new NoSuchFieldException(fieldName);
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
	}
}
