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
			return getDeclaredMethodWithExecption(clazz, name, parameterTypes);
		}
		catch(Exception e) {
			try {
				return getMethodWithExecption(clazz, name, parameterTypes);
			}
			catch(Exception e2) {
				e2.printStackTrace();
			}
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
	public static Method getDeclaredMethodWithExecption(Class<?> clazz, String name, Class<?>... parameterTypes) throws Exception
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
	 * @param fieldName the name of the method
	 *
	 * @return the {@code Method} object that matches the specified
	 * {@code clazz} and {@code name} and {@code parameterTypes}
	 */
	public static Field getField(Class<?> clazz, String fieldName)
	{
		try {
			return getDeclaredFieldWithExecption(clazz, fieldName);
		}
		catch(Exception e) {
			try {
				return getFieldWithExecption(clazz, fieldName);
			}
			catch(Exception e2) {
				e2.printStackTrace();
			}
			e.printStackTrace();
		}
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
    public static Field getFieldWithExecption(Class<?> clazz, String fieldName) throws Exception
    {
        Field field = clazz.getField(fieldName);
        if(field!=null && !field.isAccessible()) {
            setAccessible(field);
            return field;
        }

        throw new NoSuchMethodException(clazz.getName() + "." + fieldName);
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
    public static Field getDeclaredFieldWithExecption(Class<?> clazz, String fieldName) throws Exception
    {
        Field declaredField = clazz.getDeclaredField(fieldName);
        if(declaredField!=null && !declaredField.isAccessible()) {
            setAccessible(declaredField);
            return declaredField;
        }

        throw new NoSuchMethodException(clazz.getName() + "." + fieldName);
    }

	/**
	 * @param clazz Class that should contain the method
	 * @param fieldName the name of the method
	 *
	 * @return the {@code Method} object that matches the specified
	 * {@code clazz} and {@code name} and {@code parameterTypes}
	 */
	public static <T> T getField(Class<?> clazz, String fieldName, Class<T> fieldType, Object from)
	{
		// check the whole class tree
		do {
			try
			{
				// get the field of this value
				Field field=getField(clazz, fieldName);
				if(field!=null)
					return fieldType.cast(field.get(from));
			} catch(IllegalAccessException ignored) {}
		} while (clazz.getSuperclass()!=Object.class && ((clazz = clazz.getSuperclass())!=null));

		// in case of failure
		return null;
	}

    /**
     * Returns a field value from any class.
     * Note: This ignores security levels defined by the parent class.
     * @param from
     * @param fieldName
     * @param <T>
     * @return
     */
    public static <T> T getField(Object from, String fieldName)
    {
        // get class
        Class<?> clazz = from.getClass ();

        // check the whole class tree
        do {
            try
            {
                // get the field of this value
                Field field=getField(clazz, fieldName);
                if(field!=null)
                    return (T)field.get(from);
            } catch(IllegalAccessException ignored) {}
        } while (clazz.getSuperclass()!=Object.class && ((clazz = clazz.getSuperclass())!=null));

        // in case of failure
        return null;
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
