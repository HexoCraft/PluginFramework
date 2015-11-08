package com.github.hexosse.baseplugin.utils;

/*
 * Copyright 2015 Hexosse
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

import org.apache.commons.lang.WordUtils;

/**
 * This file is part BasePlugin
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class StringUtil
{
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final String SPACE_SEPARATOR = " ";
    //if text has \n, \r or \t symbols it's better to split by \s+
    private static final String SPLIT_REGEXP= "\\s+";


    /**
     * Capitalizes every first letter of a word
     *
     * @param string    String to reformat
     * @param separator Word separator
     * @return Reformatted string
     */
    public static String capitalizeFirstLetter(String string, char separator) {
        char[] separators = new char[]{separator};

        return WordUtils.capitalizeFully(string, separators).replace(String.valueOf(separator), SPACE_SEPARATOR);
    }

    /**
     * Capitalizes every first letter of a word
     *
     * @param string String to reformat
     * @return Reformatted string
     * @see com.github.hexosse.baseplugin.utils.StringUtil#capitalizeFirstLetter(String, char)
     */
    public static String capitalizeFirstLetter(String string) {
        return capitalizeFirstLetter(string, ' ');
    }


    /**
     * Split String into lines
     * @param input String to split
     * @param maxLineLength maximum char of a line
     * @return Splited string
     */
    public static String breakLines(String input, int maxLineLength)
    {
        return WordUtils.wrap(input,maxLineLength,NEWLINE,true);
        /*String[] tokens = input.split(SPLIT_REGEXP);
        StringBuilder output = new StringBuilder(input.length());
        int lineLen = 0;
        for (int i = 0; i < tokens.length; i++) {
            String word = tokens[i];

            if (lineLen + (SPACE_SEPARATOR + word).length() > maxLineLength) {
                if (i > 0) {
                    output.append(NEWLINE);
                }
                lineLen = 0;
            }
            if (i < tokens.length - 1 && (lineLen + (word + SPACE_SEPARATOR).length() + tokens[i + 1].length() <=
                    maxLineLength)) {
                word += SPACE_SEPARATOR;
            }
            output.append(word);
            lineLen += word.length();
        }
        return output.toString();*/
    }

    /**
     * Split String into lines
     * @param input String to split
     * @param maxLineLength maximum char of a line
     * @return Splited string
     */
    public static String[] breakLinesSplit(String input, int maxLineLength)
    {
        return WordUtils.wrap(input, maxLineLength, NEWLINE, true).split(NEWLINE);
        //return breakLines(input,maxLineLength).split(NEWLINE);
    }
}
