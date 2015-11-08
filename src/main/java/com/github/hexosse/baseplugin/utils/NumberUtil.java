package com.github.hexosse.baseplugin.utils;

/**
 * This file is part BasePlugin
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class NumberUtil
{

    /**
     * Checks if the string is a integer
     *
     * @param string string to check
     * @return Is the string integer?
     */
    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * @param string string to convert
     * @return integer representation of the string
     */
    public static int ToInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
