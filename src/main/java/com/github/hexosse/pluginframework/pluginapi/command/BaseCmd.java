package com.github.hexosse.pluginframework.pluginapi.command;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a base command (command without arguments)
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseCmd
{
    String description() default "";
    String usage() default "";
    String permission() default "";
    int minArgs() default 0;
    boolean allowConsole() default true;
}
