package com.darkkeeper.themaze.Basics;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by andreipiatosin on 5/15/15.
 */
public class Settings {

    public static Preferences prefs;

    public static boolean isSoundEnabled;
    public static boolean isMusicEnabled;
    public static boolean isVibrationEnabled;
    public static boolean isNigthLevelsAvailable;
    public static int levelsDone;
    public static int currentLevel;
    public static boolean isLvlWithKey;

    public static int mazeCustomWidth;
    public static String mazeCustomMethod;
    public static boolean isMazeCustomWithNight;
    public static boolean isCustomMaze;


    public static void loadSettings (){
        prefs = Gdx.app.getPreferences( "prefs" );

        isNigthLevelsAvailable      = prefs.getBoolean( "nightEndabled", true );
 //       isSoundEnabled              = prefs.getBoolean( "sound", true );
        isMusicEnabled              = prefs.getBoolean( "music", true );
        isVibrationEnabled          = prefs.getBoolean( "vibration", true );

    }

    public static void saveSettings () {
  //      prefs.putBoolean( "sound", isSoundEnabled );
        prefs.putBoolean( "music", isMusicEnabled );
        prefs.putBoolean( "vibration", isVibrationEnabled );
        prefs.putBoolean( "nightEnabled", isNigthLevelsAvailable );
        prefs.flush();
    }

    public static void loadResults () {
        levelsDone    = prefs.getInteger( "levelsDone", 0 );
        currentLevel  = prefs.getInteger( "currentLevel", 1 );
        levelsDone = 30;
    }

    public static void saveResults () {
        prefs.putInteger( "levelsDone", levelsDone );
        prefs.putInteger( "currentLevel", currentLevel );
        prefs.flush();
    }
}
