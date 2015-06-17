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
    public static TextButton.TextButtonStyle textButtonStyle;
    public static Button.ButtonStyle menuButtonStyle;
    public static TextureRegion character;
    public static Sprite flag;
    public static Sprite arrow;
   // public static TextureRegionDrawable arrowDirection;

    public static TextureAtlas textureAtlas;

    public static TextureRegion logo;

    //Background Textures
    public static Texture winterStyleTexture;
    public static Texture hellStyleTexture;
    public static Texture classicStyleTexture;

    public static Texture currentStyleTexture;

    //net Texture
//    public static TextureRegion netTextureRegion;

    //Logo Textures
    public static TextureAtlas logoTextureAtlas;
    public static TextureRegionDrawable logoBackground;

    //Menu Texures
    public static TextureAtlas menuTextureAtlas;


    public static TextureRegionDrawable defaultButtonPressed;
    public static TextureRegionDrawable defaultButton;
    public static TextureRegionDrawable menuBackground;
    public static TextureRegionDrawable levelChooseBackground;
/*    public static TextureRegionDrawable playButton;
    public static TextureRegionDrawable rateButton;
    public static TextureRegionDrawable exitButton;*/

    public static Texture loadTexture ( String file ){
        return new Texture(Gdx.files.internal( file ) );
    }

    public static void loadGame () {
        winterStyleTexture = new Texture( "game/winter_texture.jpg" );
        hellStyleTexture = new Texture ( "game/hell_texture.jpg" );
        classicStyleTexture = new Texture ( "game/classic_texture.jpg" );

        textureAtlas = new TextureAtlas( "menu/gameTextureAtlas.txt");

   //     netTextureRegion = new TextureRegion( textureAtlas.findRegion( "net_square" ) );

        character = new TextureRegion( textureAtlas.findRegion( "player" ) );
        flag = new Sprite( textureAtlas.findRegion( "flag" ) );
        arrow = new Sprite( textureAtlas.findRegion( "arrow" ) );
    }


    public static void loadMenu () {
        menuTextureAtlas = new TextureAtlas( "menu/gameTextureAtlas.txt" );
        menuBackground = new TextureRegionDrawable( menuTextureAtlas.findRegion( "mainMenuScreen" ) );
        levelChooseBackground = new TextureRegionDrawable( menuTextureAtlas.findRegion( "levelChooseScreen" ) );
        defaultButtonPressed = new TextureRegionDrawable( menuTextureAtlas.findRegion( "buttonPressed" ) );
        defaultButton = new TextureRegionDrawable( menuTextureAtlas.findRegion( "button" ) );
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
        menuButtonStyle.up = skin.newDrawable(Assets.defaultButton);
        menuButtonStyle.down = skin.newDrawable(Assets.defaultButtonPressed);
        menuButtonStyle.checked = skin.newDrawable(Assets.defaultButtonPressed);
        menuButtonStyle.over = skin.newDrawable(Assets.defaultButtonPressed);
        skin.add( "default", menuButtonStyle );

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
