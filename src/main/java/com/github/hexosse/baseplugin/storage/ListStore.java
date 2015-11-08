package com.github.hexosse.baseplugin.storage;

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

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * A list that is stored in a file
 *
 * @author <b>hexosse</b> (<a href="https://github.comp/hexosse">hexosse on GitHub</a>))
 */
public class ListStore {

    private File storageFile;

    private ArrayList<String> data;
    private boolean caseSensitive;

    /**
     * @param storageFile		The file where the list will be stored.
     * @param caseSensitive		If set to false all comparison will be done in lower-case.
     */
    public ListStore(File storageFile, boolean caseSensitive)
    {
        this.storageFile = storageFile;

        this.data = new ArrayList<String>();
        this.caseSensitive = caseSensitive;

        if (this.storageFile.exists() == false){
            try{
                this.storageFile.createNewFile();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Loads the current lust entries from the file.
     */
    public void load(){
        try{
            DataInputStream input = new DataInputStream(new FileInputStream(this.storageFile));
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line, entry;

            while ((line = reader.readLine()) != null){
                entry = (this.caseSensitive) ? line : line.toLowerCase();

                if (this.data.contains(entry) == false){
                    this.data.add(entry);
                }
            }

            reader.close();
            input.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Writes the list entries to the file.
     */
    public void save(){
        try{
            FileWriter stream = new FileWriter(this.storageFile);
            BufferedWriter out = new BufferedWriter(stream);

            for (String entry : this.data){
                out.write(entry);
                out.newLine();
            }

            out.close();
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Check to see if the list contains a value.
     *
     * @param entry		The value to check for.
     * @return			True of the key exists false if not.
     */
    public boolean contains(String entry){
        return this.data.contains((this.caseSensitive) ? entry : entry.toLowerCase());
    }

    /**
     * Gets all of the entries in the list.
     *
     * @return	The entries.
     */
    public List<String> getAll(){
        return this.data;
    }

    /**
     * Gets all of the keys in the list.
     *
     * @return	The keys.
     */
    public Integer size(){
        return this.data.size();
    }

    /**
     * Adds a value to the list.
     *
     * @param entry		The value.
     */
    public void add(String entry){
        if (!this.caseSensitive){
            entry = entry.toLowerCase();
        }

        if (!this.data.contains(entry)){
            this.data.add(entry);

            try{
                FileWriter stream = new FileWriter(this.storageFile, true);
                BufferedWriter out = new BufferedWriter(stream);

                out.write(entry);
                out.newLine();

                out.close();
                stream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Removes a value from the list.
     *
     * @param entry		The value.
     */
    public void remove(String entry){
        this.data.remove((this.caseSensitive) ? entry : entry.toLowerCase());
        this.save();
    }

    /**
     * Removes all entries from the list.
     */
    public void removeAll(){
        this.data.clear();
        this.save();
    }

}
