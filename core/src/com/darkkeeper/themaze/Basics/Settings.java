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
    public static boolean isRated;
    public static int currentDayOfTheYear;
    public static int daysInARow;

    public static int stopWatchesAmount;

    public static int levelsDone;
    public static int currentLevel;


    public static boolean isLvlWithKey;

    public static int mazeCustomWidth;
    public static String mazeCustomMethod;
    public static boolean isMazeCustomWithNight;
    public static boolean isCustomMaze;


    public static void loadSettings (){
        prefs = Gdx.app.getPreferences( "prefs" );

        isRated                     = prefs.getBoolean( "isRated", false );
        isNigthLevelsAvailable      = prefs.getBoolean( "nightEndabled", true );
 //       isSoundEnabled              = prefs.getBoolean( "sound", true );
        isMusicEnabled              = prefs.getBoolean( "music", true );
        isVibrationEnabled          = prefs.getBoolean( "vibration", true );

        currentDayOfTheYear         = prefs.getInteger( "day", 0 );
        daysInARow                  = prefs.getInteger( "daysInARow", 0 );
    }

    public static void saveSettings () {
  //      prefs.putBoolean( "sound", isSoundEnabled );
        prefs.putBoolean( "rateStatus", isRated );
        prefs.putBoolean( "music", isMusicEnabled );
        prefs.putBoolean( "vibration", isVibrationEnabled );
        prefs.putBoolean( "nightEnabled", isNigthLevelsAvailable );
        prefs.putInteger( "day", currentDayOfTheYear );
        prefs.putInteger( "daysInARow", daysInARow );
        prefs.flush();
    }

    public static void saveResults () {
        prefs.putInteger( "levelsDone", levelsDone );
        prefs.putInteger( "currentLevel", currentLevel );
        prefs.flush();
    }

    public static void loadResults () {
        levelsDone    = prefs.getInteger( "levelsDone", 0 );
        currentLevel  = prefs.getInteger( "currentLevel", 1 );
    //    levelsDone = 30;
    }

    public static void loadStopWatchesAmount () {
        stopWatchesAmount = prefs.getInteger( "stopWatchesAmount", 0 );
    }

    public static void saveStopWatchesAmount () {
        prefs.putInteger( "stopWatchesAmount", stopWatchesAmount );
        prefs.flush();
    }


}
