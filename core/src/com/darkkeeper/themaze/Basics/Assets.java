package com.darkkeeper.themaze.Basics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import javax.xml.soap.Text;


/**
 * Created by andreipiatosin on 5/15/15.
 */
public class Assets {
    public static Skin skin;
    public static Button.ButtonStyle menuButtonStyle;
    public static Button.ButtonStyle arrowButtonStyle;

    public static Slider.SliderStyle sliderStyle;
    public static TextureRegionDrawable sliderKnob;
    public static TextureRegionDrawable sliderBackground;

    public static TextureAtlas digitsTextureAtlas;
    public static TextureRegionDrawable digitTextureRegionDrawable;

    public static TextureRegionDrawable checker;

    public static Sprite door;
    public static Sprite arrowDirection;
   // public static TextureRegionDrawable arrowDirection;

    public static TextureAtlas textureAtlas;

    public static TextureRegion logo;

    //Background Textures
    public static Texture gameTexture1;

/*
    public static Texture winterStyleTexture;
    public static Texture hellStyleTexture;
    public static Texture classicStyleTexture;
*/


    public static Texture currentStyleTexture;



    //Logo Textures
    public static TextureAtlas logoTextureAtlas;
    public static TextureRegionDrawable logoBackground;

    //Menu Texures
    public static TextureAtlas menuTextureAtlas;
    public static TextureAtlas menu2TextureAtlas;


    public static TextureRegionDrawable defaultButtonPressed;
    public static TextureRegionDrawable defaultButton;

    public static TextureRegionDrawable arrowButtonPressed;
    public static TextureRegionDrawable arrowButton;
    public static TextureRegionDrawable arrowButtonImpossible;

    public static TextureRegionDrawable menuBackground;
    public static TextureRegionDrawable customLevelBackground;
    public static TextureRegionDrawable levelChooseBackground;
    public static TextureRegionDrawable gameOverBackground;


    public static Texture loadTexture ( String file ){
        return new Texture(Gdx.files.internal( file ) );
    }

    public static void loadGame () {
        gameTexture1 = new Texture( "game/gameTexture3.jpg" );

        textureAtlas = new TextureAtlas( "game/gameTextureAtlas.txt");

   //     netTextureRegion = new TextureRegion( textureAtlas.findRegion( "net_square" ) );

        door = new Sprite( textureAtlas.findRegion( "door" ) );
        arrowDirection = new Sprite( textureAtlas.findRegion( "arrow" ) );
    }


    public static void loadMenu () {
        menuTextureAtlas = new TextureAtlas( "menu/menuTextureAtlas.txt" );
        menuBackground = new TextureRegionDrawable( menuTextureAtlas.findRegion( "mainScreen" ) );
        customLevelBackground = new TextureRegionDrawable( menuTextureAtlas.findRegion( "customScreen" ) );
        defaultButtonPressed = new TextureRegionDrawable( menuTextureAtlas.findRegion( "sliderKnob" ) );
        defaultButton = new TextureRegionDrawable( menuTextureAtlas.findRegion( "sliderKnob" ) );
        sliderKnob = new TextureRegionDrawable( menuTextureAtlas.findRegion( "sliderKnob" ) );
        sliderBackground = new TextureRegionDrawable( menuTextureAtlas.findRegion( "sliderKnob" ) );
        checker = new TextureRegionDrawable( menuTextureAtlas.findRegion( "checker" ) );

        menu2TextureAtlas = new TextureAtlas( "menu/menu2TextureAtlas.txt" );
        levelChooseBackground = new TextureRegionDrawable( menu2TextureAtlas.findRegion( "levelChooserScreen" ) );
        gameOverBackground = new TextureRegionDrawable( menu2TextureAtlas.findRegion( "gameOverScreen" ) );

        arrowDirection = new Sprite( menu2TextureAtlas.findRegion( "levelArrow" ) );
        
        arrowButton = new TextureRegionDrawable( menu2TextureAtlas.findRegion( "levelArrow" ) );
        arrowButtonPressed = new TextureRegionDrawable( menu2TextureAtlas.findRegion( "levelArrowPressed" ) );
        arrowButtonImpossible = new TextureRegionDrawable( menu2TextureAtlas.findRegion( "levelArrowImpossible" ) );
    }

    public static void loadDigit ( int i ) {
        digitsTextureAtlas = new TextureAtlas( "menu/digitsTextureAtlas.txt" );
        digitTextureRegionDrawable = new TextureRegionDrawable( digitsTextureAtlas.findRegion( "" + i ) );

    }

    public static void loadLogo() {
        logoTextureAtlas = new TextureAtlas( "logo/logoTextureAtlas.txt" );
        logoBackground = new TextureRegionDrawable( logoTextureAtlas.findRegion( "logoScreen" ) );
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

        menuButtonStyle = new Button.ButtonStyle();
        menuButtonStyle.up = skin.newDrawable(Assets.defaultButton,1f,1f,1f,0f);
        menuButtonStyle.down = skin.newDrawable(Assets.defaultButtonPressed,1f,1f,1f,0f);
        menuButtonStyle.checked = skin.newDrawable(Assets.defaultButtonPressed,1f,1f,1f,0f);
        menuButtonStyle.over = skin.newDrawable(Assets.defaultButtonPressed,1f,1f,1f,0f);
        skin.add( "default", menuButtonStyle );


        arrowButtonStyle = new Button.ButtonStyle();
        arrowButtonStyle.up = skin.newDrawable(Assets.arrowButton);
        arrowButtonStyle.down = skin.newDrawable(Assets.arrowButtonPressed);
        arrowButtonStyle.checked = skin.newDrawable(Assets.arrowButtonImpossible);
        arrowButtonStyle.over = skin.newDrawable(Assets.arrowButton);
        skin.add( "arrow", arrowButtonStyle );

        sliderStyle = new Slider.SliderStyle();
        sliderStyle.knob = skin.newDrawable( Assets.sliderKnob );
        sliderStyle.background = skin.newDrawable( Assets.sliderBackground, 1f,1f,1f,0f );
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
