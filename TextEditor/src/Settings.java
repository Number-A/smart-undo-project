//package src;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

/*
    Settings class: Reads/writes to settings.cfg file

    Settings()
    if exists(settings.cfg) then
        - read file and set properties to appropriate settings
    else
        - initialize a settings.cfg file with default setting values
        - read file and set properties to appropriate settings

    read()
        - scans settings.cfg line by line, where line is a key-value pair, and store them in a Hash map

    write()
        - for each key value pair from Hash map, write them line by line to settings.cfg file (overwrites old data)

    init()
        - creates a settings.cfg file with default values

    save()
        - accessor method for read()
*/

public class Settings {
    private String filePath;

    public static final int DEFAULT_FONT_SIZE = 11;
    public static final String DEFAULT_LANGUAGE = "en";
    public static final int DEFAULT_MAX_GROUP_EDITS = 20;

    private final String FONT_SIZE = "font_size";
    private final String LANGUAGE = "language";
    private final String MAX_GROUP_EDITS = "max_group_edits";

    private final int SETTING_KEY = 0;
    private final int SETTING_VALUE = 1;

    private HashMap<String, Object> settings;

    /*
    ==== ==== ==== ==== ==== ==== ==== ====
    Constructor(s)
    ==== ==== ==== ==== ==== ==== ==== ====
    */
    public Settings() {
        initSettings("settings.cfg");
    }

    public Settings(String fileName) {
        initSettings(fileName);
    }

    private void initSettings(String fileName) {

        filePath = fileName;

        settings = new HashMap<>();

        File file = new File(filePath);

        // check if settings.cfg already exists
        if(file.exists() && !file.isDirectory()) {

            //file exists, load settings
            try {
                read();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // file does not exist, initialize with default values
        } else {
            try {
                init();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    ==== ==== ==== ==== ==== ==== ==== ====
    Getters/Setters
    ==== ==== ==== ==== ==== ==== ==== ====
     */

    public int getFontSize() {
        return (int) settings.get(FONT_SIZE);
    }

    public String getLanguage() {
        return (String) settings.get(LANGUAGE);
    }

    public int getMaxGroupEdits() {
        return (int) settings.get(MAX_GROUP_EDITS);
    }

    public void setFontSize(int fontSize) {
        settings.put(FONT_SIZE, fontSize);
    }

    public void setLanguage(String language) {
        settings.put(LANGUAGE, language);
    }

    public void setMaxGroupEdits(int maxGroupEdits) {
        settings.put(MAX_GROUP_EDITS, maxGroupEdits);
    }

    /*
    ==== ==== ==== ==== ==== ==== ==== ====
    Methods
    ==== ==== ==== ==== ==== ==== ==== ====
     */

    // reads and stores data from settings.cfg file
    private void read() throws FileNotFoundException {

        Scanner reader = new Scanner(new FileInputStream(filePath));

        // read all settings
        while(reader.hasNextLine()) {
            String line = reader.nextLine();
            String[] setting = line.split("=");

            // given setting key, set properties to setting value
            switch(setting[SETTING_KEY]) {
                case FONT_SIZE:
                    settings.put(FONT_SIZE, Integer.parseInt(setting[SETTING_VALUE]));
                    break;
                case MAX_GROUP_EDITS:
                    settings.put(MAX_GROUP_EDITS, Integer.parseInt(setting[SETTING_VALUE]));
                    break;
                case LANGUAGE:
                    settings.put(LANGUAGE, setting[SETTING_VALUE]);
                    break;
            }
        }
        reader.close();
    }

    // writes to settings.cfg file
    private void write() throws IOException {
        FileWriter fw = new FileWriter(filePath);

        for(String setting: settings.keySet()) {
            String line = setting + "=" + settings.get(setting) + "\n";
            fw.write(line);
        }
        fw.close();
    }

    // creates a settings.cfg file with default values
    private void init() throws IOException {

        // write
        FileWriter fw = new FileWriter(filePath);

        fw.write(MAX_GROUP_EDITS + "=" + DEFAULT_MAX_GROUP_EDITS + "\n");
        fw.write(FONT_SIZE + "=" + DEFAULT_FONT_SIZE + "\n");
        fw.write(LANGUAGE + "=" + DEFAULT_LANGUAGE);

        fw.close();

        // read
        read();
    }

    // given current values of properties, write them to settings.cfg file
    public void save() {
        try {
            write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}