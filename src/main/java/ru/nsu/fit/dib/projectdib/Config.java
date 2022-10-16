package ru.nsu.fit.dib.projectdib;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class Config {
    public static int WINDOW_HEIGHT;
    public static int WINDOW_WIDTH;
    public static String WINDOW_MODE;
    static void setConfig(String filepath)
    {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(new File(filepath)));

            WINDOW_WIDTH = Integer.parseInt(props.getProperty("WINDOW_WIDTH","1280"));
            WINDOW_HEIGHT = Integer.parseInt(props.getProperty("WINDOW_HEIGHT","720"));
            WINDOW_MODE = props.getProperty("WINDOW_MODE","Window");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
