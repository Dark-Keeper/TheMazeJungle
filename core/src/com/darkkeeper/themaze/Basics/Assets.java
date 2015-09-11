package com.darkkeeper.themaze.Basics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import javax.xml.soap.Text;


/**
 * Created by andreipiatosin on 5/15/15.
 */
public class Assets {
    public static Skin skin;
    public static Button.ButtonStyle checkBoxButtonStyle;
    public static Button.ButtonStyle arrowButtonStyle;
    public static Button.ButtonStyle keyTipButtonStyle;

    public static Slider.SliderStyle sliderStyle;

    public static TextureAtlas digitsTextureAtlas;
    public static TextureRegionDrawable digitTextureRegionDrawable;


    //Game TEXTURES
    public static Sprite door;
    public static Sprite key;
    public static Sprite arrowDirection;
    public static TextureRegionDrawable keyTip;

    //GAME UI
    public static Label.LabelStyle uiTimerLabelStyle;
    public static TextureRegion bottomBarBackground;
    public static TextureRegion bottomBarZoomButton;
    public static TextureRegion bottomBarExitButton;
    public static TextureRegion bottomBarStopWatchButton;
    public static TextureRegion bottomBarPauseButton;
    public static TextureRegion[] bottomBarSoundButton;


    public static TextureAtlas textureAtlas;

    public static TextureRegion logo;

    //Background Textures
    public static Texture gameTexture1;
    public static Texture currentStyleTexture;


    //Logo Textures
/*    public static TextureAtlas logoTextureAtlas;
    public static TextureRegionDrawable logoBackground;*/
    public static Texture logoTexture;
    public static TextureRegionDrawable logoTextureDrawable;


    //Menu Texures
    public static TextureAtlas menuTextureAtlas;
    public static TextureAtlas menu2TextureAtlas;

    public static TextureAtlas backgrounds1TextureAtlas;
    public static TextureAtlas menuElementsTextureAtlas;

    public static TextureRegionDrawable mainMenuBackgroundTextureRegion;
    public static TextureRegionDrawable gameOverBackgroundTextureRegion;

    public static TextureRegionDrawable arrowButton;
    public static TextureRegionDrawable arrowButtonPressed;
    public static TextureRegionDrawable arrowButtonImpossible;
    public static TextureRegionDrawable sliderTextureRegionDrawable;
    public static TextureRegionDrawable sliderKnobTextureRegionDrawable;
    public static TextureRegionDrawable checkBoxTextureRegionDrawable;
    public static TextureRegionDrawable checkBoxCheckedTextureRegionDrawable;

    public static TextureRegion boxCheckerTextureRegion;
    public static TextureRegion dailyQuestsBoxTextureRegion;


    public static TextureRegion playBtnTextureRegion;
    public static TextureRegion widthBtnTextureRegion;
    public static TextureRegion methodBtnTextureRegion;
    public static TextureRegion shareBtnTextureRegion;
    public static TextureRegion restartBtnTextureRegion;
    public static TextureRegion menuBtnTextureRegion;
    public static TextureRegion nextBtnTextureRegion;
    public static TextureRegion okBtnTextureRegion;
    public static TextureRegion levelBtnTextureRegion;
    public static TextureRegion campaignBtnTextureRegion;
    public static TextureRegion helpBtnTextureRegion;
    public static TextureRegion customBtnTextureRegion;
    public static TextureRegion optionsBtnTextureRegion;
    public static TextureRegion nightBtnTextureRegion;
    public static TextureRegion nightLevelsBtnTextureRegion;
    public static TextureRegion vibrationsBtnTextureRegion;
    public static TextureRegion musicBtnTextureRegion;

    public static TextureRegion reckBackTextureRegion;
    public static TextureRegion primMethodTextureRegion;
    public static TextureRegion huntAndKillkMethodTextureRegion;

    public static Music menuMusic;


    public static Texture loadTexture ( String file ){
        return new Texture(Gdx.files.internal( file ) );
    }

    public static void loadGame () {
        gameTexture1 = new Texture( "game/gameTexture3.png" );

        textureAtlas = new TextureAtlas( "game/gameTextureAtlas.txt");

        door             = new Sprite( textureAtlas.findRegion( "door" ) );
        key              = new Sprite( textureAtlas.findRegion( "key" ) );
        arrowDirection   = new Sprite( textureAtlas.findRegion( "arrow" ) );


        keyTip           = new TextureRegionDrawable ( textureAtlas.findRegion( "keyTip" ) );


        bottomBarBackground = new TextureRegion( textureAtlas.findRegion( "bbBackground" ) );
        bottomBarExitButton = new TextureRegion( textureAtlas.findRegion( "bbExitBtn" ) );
        bottomBarZoomButton = new TextureRegion( textureAtlas.findRegion( "bbZoomBtn" ) );
     //   bottomBarStopWatchButton    = new TextureRegion( textureAtlas.findRegion( "bbStopWatchBtn" ) );

    }


    public static void loadMenu () {
        loadMusic();

        backgrounds1TextureAtlas = new TextureAtlas( "menu/backgroundsTexturePack1.atlas" );

        mainMenuBackgroundTextureRegion = new TextureRegionDrawable( backgrounds1TextureAtlas.findRegion( "mainMenuScreenBackground" ) );
        gameOverBackgroundTextureRegion = new TextureRegionDrawable( backgrounds1TextureAtlas.findRegion( "gameOverScreenBackground" ) );


        menuElementsTextureAtlas = new TextureAtlas( "menu/elementsTexturePack1.atlas" );

        arrowButton                         = new TextureRegionDrawable( menuElementsTextureAtlas.findRegion( "levelArrow" ) );
        arrowButtonPressed                  = new TextureRegionDrawable( menuElementsTextureAtlas.findRegion( "levelArrowPressed" ) );
        arrowButtonImpossible               = new TextureRegionDrawable( menuElementsTextureAtlas.findRegion( "levelArrowImpossible" ) );

        sliderTextureRegionDrawable         = new TextureRegionDrawable( menuElementsTextureAtlas.findRegion( "slider" ) );
        sliderKnobTextureRegionDrawable     = new TextureRegionDrawable( menuElementsTextureAtlas.findRegion( "sliderKnob" ) );
        checkBoxTextureRegionDrawable       = new TextureRegionDrawable( menuElementsTextureAtlas.findRegion( "checkBox" ) );
        checkBoxCheckedTextureRegionDrawable= new TextureRegionDrawable( menuElementsTextureAtlas.findRegion( "checkBoxChecked" ) );


        boxCheckerTextureRegion             = new TextureRegion( menuElementsTextureAtlas.findRegion( "boxChecker" ) );
        dailyQuestsBoxTextureRegion         = new TextureRegion( menuElementsTextureAtlas.findRegion( "dailyQuestsBox" ) );

        reckBackTextureRegion               = new TextureRegion( menuElementsTextureAtlas.findRegion( "rbMethodButton" ) );
        primMethodTextureRegion             = new TextureRegion( menuElementsTextureAtlas.findRegion( "primMethodButton" ) );
        huntAndKillkMethodTextureRegion     = new TextureRegion( menuElementsTextureAtlas.findRegion( "h&kMethodButton" ) );
        playBtnTextureRegion                = new TextureRegion( menuElementsTextureAtlas.findRegion( "playButton" ) );
        widthBtnTextureRegion               = new TextureRegion( menuElementsTextureAtlas.findRegion( "widthButton" ) );
        methodBtnTextureRegion              = new TextureRegion( menuElementsTextureAtlas.findRegion( "methodButton" ) );
        shareBtnTextureRegion               = new TextureRegion( menuElementsTextureAtlas.findRegion( "shareButton" ) );
        restartBtnTextureRegion             = new TextureRegion( menuElementsTextureAtlas.findRegion( "restartButton" ) );
        menuBtnTextureRegion                = new TextureRegion( menuElementsTextureAtlas.findRegion( "menuButton" ) );
        nextBtnTextureRegion                = new TextureRegion( menuElementsTextureAtlas.findRegion( "nextButton" ) );
        okBtnTextureRegion                  = new TextureRegion( menuElementsTextureAtlas.findRegion( "okButton" ) );
        levelBtnTextureRegion               = new TextureRegion( menuElementsTextureAtlas.findRegion( "levelButton" ) );
        campaignBtnTextureRegion            = new TextureRegion( menuElementsTextureAtlas.findRegion( "campaignButton" ) );
        helpBtnTextureRegion                = new TextureRegion( menuElementsTextureAtlas.findRegion( "helpButton" ) );
        customBtnTextureRegion              = new TextureRegion( menuElementsTextureAtlas.findRegion( "customButton" ) );
        optionsBtnTextureRegion             = new TextureRegion( menuElementsTextureAtlas.findRegion( "optionsButton" ) );
        nightBtnTextureRegion               = new TextureRegion( menuElementsTextureAtlas.findRegion( "nightButton" ) );
        nightLevelsBtnTextureRegion         = new TextureRegion( menuElementsTextureAtlas.findRegion( "nightLevelsButton" ) );
        vibrationsBtnTextureRegion          = new TextureRegion( menuElementsTextureAtlas.findRegion( "vibrationsButton" ) );
        musicBtnTextureRegion               = new TextureRegion( menuElementsTextureAtlas.findRegion( "musicButton" ) );

        bottomBarStopWatchButton            = new TextureRegion( menuElementsTextureAtlas.findRegion( "stopWatch" ) );

    }

    public static void loadDigit ( int i ) {
        digitsTextureAtlas = new TextureAtlas( "menu/digitsTextureAtlas.txt" );
        digitTextureRegionDrawable = new TextureRegionDrawable( digitsTextureAtlas.findRegion( "" + i ) );

    }

    public static void loadLogo() {
/*        logoTextureAtlas = new TextureAtlas( "logo/logoTextureAtlas.txt" );
        logoBackground = new TextureRegionDrawable( logoTextureAtlas.findRegion( "logoScreen" ) );*/
        //logoTexture = new Texture( "logo/logo.jpg" );
     //   logoTextureDrawable = new TextureRegionDrawable()
    }

    public static void loadMusic (){
        menuMusic = Gdx.audio.newMusic(Gdx.files.internal ( "audio/menuMusic.ogg") );
    }

    public static void loadFonts () {

        skin = new Skin();
 /*       Pixmap pixmap = new Pixmap ( 1,1, Pixmap.Format.RGBA8888 );
        pixmap.setColor( Color.BLACK );
        pixmap.fill();
        skin.add( "black", new Texture (pixmap) );


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal( "fonts/segoe-script-bold.ttf" ));
        String CHARS = "";
        for ( int i = 32; i < 127; i ++){
            CHARS += (char) i;
        }
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 55;
        parameter.characters = CHARS;
        BitmapFont defaultFont = generator.generateFont( parameter );
        skin.add( "default", defaultFont );
        generator.dispose();


        textButtonStyle             = new TextButton.TextButtonStyle();
        textButtonStyle.fontColor   = Color.BLUE;
        textButtonStyle.up          = skin.newDrawable( "black", 0.5f, 0.5f, 0.5f, 0.5f );
        textButtonStyle.down        = skin.newDrawable( "black", 0.5f, 0.5f, 0.5f, 0.5f );
        textButtonStyle.checked     = skin.newDrawable( "black", 0.5f, 0.5f, 0.5f, 0.5f );
        textButtonStyle.over        = skin.newDrawable( "black", 0.5f, 0.5f, 0.5f, 0.5f );
        textButtonStyle.font = defaultFont;
        skin.add( "default", textButtonStyle );*/


        FreeTypeFontGenerator generator = new FreeTypeFontGenerator( Gdx.files.internal( "fonts/segoe-script-bold.ttf" ));
        String CHARS = "0123456789:";
/*        for ( int i = 32; i < 127; i ++){
            CHARS += (char) i;
        }*/

        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 55;
        parameter.characters = CHARS;
        BitmapFont defaultFont = generator.generateFont( parameter );
        skin.add( "default", defaultFont );
        generator.dispose();


        uiTimerLabelStyle = new Label.LabelStyle();
        uiTimerLabelStyle.fontColor = Color.WHITE;
        uiTimerLabelStyle.font = defaultFont;
        skin.add( "default", uiTimerLabelStyle );


        checkBoxButtonStyle = new Button.ButtonStyle();
        checkBoxButtonStyle.up = skin.newDrawable(Assets.checkBoxTextureRegionDrawable,1f,1f,1f,1f);
        checkBoxButtonStyle.down = skin.newDrawable(Assets.checkBoxTextureRegionDrawable,1f,1f,1f,1f);
        checkBoxButtonStyle.checked = skin.newDrawable(Assets.checkBoxCheckedTextureRegionDrawable,1f,1f,1f,1f);
        checkBoxButtonStyle.over = skin.newDrawable(Assets.checkBoxTextureRegionDrawable,1f,1f,1f,1f);
        skin.add( "checkBox", checkBoxButtonStyle );

        arrowButtonStyle = new Button.ButtonStyle();
        arrowButtonStyle.up = skin.newDrawable(Assets.arrowButton);
        arrowButtonStyle.down = skin.newDrawable(Assets.arrowButtonPressed);
        arrowButtonStyle.checked = skin.newDrawable(Assets.arrowButtonImpossible);
        arrowButtonStyle.over = skin.newDrawable(Assets.arrowButton);
        skin.add( "arrow", arrowButtonStyle );

        keyTipButtonStyle = new Button.ButtonStyle();
        keyTipButtonStyle.up = skin.newDrawable( Assets.keyTip );
        keyTipButtonStyle.down = skin.newDrawable( Assets.keyTip );
        keyTipButtonStyle.checked = skin.newDrawable( Assets.keyTip );
        keyTipButtonStyle.over = skin.newDrawable( Assets.keyTip );
        skin.add( "keyTip", keyTipButtonStyle );


        sliderStyle = new Slider.SliderStyle();
        sliderStyle.knob = skin.newDrawable( Assets.sliderKnobTextureRegionDrawable );
        sliderStyle.background = skin.newDrawable( Assets.sliderTextureRegionDrawable, 1f,1f,1f,1f );
        skin.add( "default", sliderStyle );


/*
        menuButtonStyle = new Button.ButtonStyle();
        menuButtonStyle.up = skin.newDrawable(Assets.rateButton);
        menuButtonStyle.down = skin.newDrawable(Assets.rateButton);
        menuButtonStyle.checked = skin.newDrawable(Assets.rateButton);
        menuButtonStyle.over = skin.newDrawable(Assets.rateButton);
        skin.add( "rate", menuButtonStyle );

        menuButtonStyle = new Button.ButtonStyle();
        menuButtonStyle.up = skin.newDrawable(Assets.exitButton);
        menuButtonStyle.down = skin.newDrawable(Assets.exitButton);
        menuButtonStyle.checked = skin.newDrawable(Assets.exitButton);
        menuButtonStyle.over = skin.newDrawable(Assets.exitButton);
        skin.add( "exit", menuButtonStyle );*/

/*
        arrowButtonStyle = new Button.ButtonStyle();
        arrowButtonStyle.up = skin.newDrawable(Assets.arrowDirection);
        arrowButtonStyle.down = skin.newDrawable(Assets.arrowDirection);
        arrowButtonStyle.checked = skin.newDrawable(Assets.arrowDirection);
        arrowButtonStyle.over = skin.newDrawable(Assets.arrowDirection);
        skin.add( "arrow", arrowButtonStyle );*/
    }


}
