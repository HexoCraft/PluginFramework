package com.github.hexosse.pluginframework.pluginapi.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Represents a sub-command
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SubCmd
{
    String name() default "";
    String description() default "";
    String usage() default "";
    String permission() default "";
    int minArgs() default 0;
    boolean allowConsole() default true;
}
